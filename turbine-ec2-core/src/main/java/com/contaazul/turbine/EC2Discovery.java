package com.contaazul.turbine;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.contaazul.turbine.ec2.AWSCredentialsFromProperties;
import com.contaazul.turbine.ec2.ClusterList;
import com.contaazul.turbine.ec2.InstanceList;
import com.netflix.turbine.discovery.Instance;
import com.netflix.turbine.discovery.InstanceDiscovery;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EC2 instance discovery.
 * @author Carlos Alexandro Becker
 */
public final class EC2Discovery implements InstanceDiscovery {
    /**
     * Config.
     */
    private final transient Config config;

    /**
     * Ec2 client.
     */
    private final transient AmazonEC2Client client;

    /**
     * Ctor using configs from properties.
     */
    public EC2Discovery() {
        this(
            new Config.FromProperties(),
            new AmazonEC2Client(new AWSCredentialsFromProperties())
        );
    }

    /**
     * Ctor.
     * @param config Config implementation.
     * @param client Ec2 client.
     */
    public EC2Discovery(final Config config, final AmazonEC2Client client) {
        this.config = config;
        this.client = client;
    }

    @Override
    public Collection<Instance> getInstanceList() throws Exception {
        return new ClusterList(this.config).get()
            .parallelStream()
            .map(cluster -> new InstanceList(
                    cluster, this.config, this.client
                ).get()
            ).flatMap(List::stream)
            .collect(Collectors.toList());
    }
}
