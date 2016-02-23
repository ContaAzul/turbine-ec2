package com.contaazul.turbine;

import com.contaazul.turbine.ec2.AWSCredentialsFromProperties;
import com.contaazul.turbine.ec2.ClusterList;
import com.contaazul.turbine.ec2.EC2ClientProvider;
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
     * Ec2 client provider.
     */
    private final transient EC2ClientProvider provider;

    /**
     * Ctor using configs from properties.
     */
    public EC2Discovery() {
        this(
            new Config.FromProperties(),
            new EC2ClientProvider.Smart(
                new AWSCredentialsFromProperties(),
                new Config.FromProperties()
            )
        );
    }

    /**
     * Ctor.
     * @param config Config implementation.
     * @param provider AWS credentials.
     */
    public EC2Discovery(final Config config, final EC2ClientProvider provider) {
        this.config = config;
        this.provider = provider;
    }

    @Override
    public Collection<Instance> getInstanceList() throws Exception {
        return new ClusterList(this.config).get()
            .parallelStream()
            .map(cluster -> new InstanceList(
                    cluster, this.config, this.provider
                ).get()
            ).flatMap(List::stream)
            .collect(Collectors.toList());
    }
}
