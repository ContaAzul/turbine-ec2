package com.contaazul.turbine.ec2;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Reservation;
import com.contaazul.turbine.Config;
import com.netflix.turbine.discovery.Instance;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Instance listing from ec2.
 * @author Carlos Alexandro Becker
 */
public final class InstanceList {
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
     * @param credentials AWS credentials.
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
        final EC2ToTurbineInstance converter = new EC2ToTurbineInstance(
            this.cluster
        );
        return this.client
            .describeInstances(this.request())
            .getReservations()
            .stream()
            .map(Reservation::getInstances)
            .flatMap(List::stream)
            .map(converter::convert)
            .collect(Collectors.toList());
    }

    /**
     * Builds a describe instance request.
     * @return Request.
     */
    private DescribeInstancesRequest request() {
        final Filter filter = new Filter()
            .withName(new ClusterTag(this.cluster, this.config).get())
            .withValues(this.cluster);
        return new DescribeInstancesRequest().withFilters(filter);
    }
}
