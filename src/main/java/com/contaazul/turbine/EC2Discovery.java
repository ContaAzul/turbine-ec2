package com.contaazul.turbine;

import com.amazonaws.auth.AWSCredentials;
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
     * AWS credentials.
     */
    private final transient AWSCredentials credentials;

    /**
     * Ctor using configs from properties.
     */
    public EC2Discovery() {
        this(new Config.FromProperties(), new AWSCredentialsFromProperties());
    }

    /**
     * Ctor.
     * @param config Config implementation.
     * @param credentials AWS credentials.
     */
    public EC2Discovery(final Config config, final AWSCredentials credentials) {
        this.config = config;
        this.credentials = credentials;
    }

    @Override
    public Collection<Instance> getInstanceList() throws Exception {
        return new ClusterList(this.config).get()
            .parallelStream()
            .map(cluster -> new InstanceList(cluster, this.credentials).get())
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }
}
