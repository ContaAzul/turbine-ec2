package com.contaazul.turbine.ec2;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Reservation;
import com.contaazul.turbine.Config;
import com.netflix.turbine.discovery.Instance;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Instance listing from ec2.
 * @author Carlos Alexandro Becker
 */
public final class InstanceList {
    /**
     * Logger.
     */
    private static final Logger LOGGER =
        LoggerFactory.getLogger(InstanceList.class);

    /**
     * Cluster.
     */
    private final transient String cluster;

    /**
     * AWS ec2 client.
     */
    private final transient AmazonEC2Client client;

    /**
     * Config.
     */
    private final transient Config config;

    /**
     * Ctor.
     * @param cluster Cluster.
     * @param client AWS client.
     * @param config Config.
     */
    public InstanceList(
        final String cluster,
        final Config config,
        final AmazonEC2Client client
    ) {
        this.cluster = cluster;
        this.client = client;
        this.config = config;
    }

    /**
     * Gets the list of ec2 instances of a given cluster.
     * @return List of instances.
     */
    public List<Instance> get() {
        final ClusterTag tag = new ClusterTag(this.cluster, this.config);
        final String name = tag.name();
        final String value = tag.value();
        InstanceList.LOGGER.info(
            String.format(
                "Looking for instances for '%s' filtering with '%s'='%s'",
                this.cluster,
                name,
                value
            )
        );
        final EC2ToTurbineInstance converter = new EC2ToTurbineInstance(
            this.cluster
        );
        return this.client
            .describeInstances(this.request(name, value))
            .getReservations()
            .parallelStream()
            .map(Reservation::getInstances)
            .flatMap(List::stream)
            .map(converter::convert)
            .collect(Collectors.toList());
    }

    /**
     * Builds a describe instance request.
     * @return Request.
     */
    private DescribeInstancesRequest request(
        final String tag,
        final String value
    ) {
        final Filter filter = new Filter()
            .withName(tag)
            .withValues(value);
        return new DescribeInstancesRequest().withFilters(filter);
    }
}
