package com.contaazul;

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
     * Ctor using configs from properties.
     */
    public EC2Discovery() {
        this(new Config.FromProperties());
    }

    /**
     * Ctor.
     * @param config config implementation.
     */
    public EC2Discovery(final Config config) {
        this.config = config;
    }

    @Override
    public Collection<Instance> getInstanceList() throws Exception {
        return new ClusterList(this.config)
            .get()
            .parallelStream()
            .map(cluster -> new InstanceList(cluster).get())
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }
}
