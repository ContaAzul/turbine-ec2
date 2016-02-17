package com.contaazul.turbine.ec2;

import com.contaazul.turbine.Config;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lists the clusters configured in the properties file.
 * @author Carlos Alexandro Becker
 */
public final class ClusterList {
    /**
     * Config.
     */
    private final transient Config config;

    /**
     * Ctor.
     * @param config Config.
     */
    public ClusterList(Config config) {
        this.config = config;
    }

    /**
     * Returns the list of configured clusters.
     * @return List of clusters.
     */
    public List<String> get() {
        return Lists.newArrayList(this.config.clusters().trim().split(","))
            .stream()
            .filter(cluster -> !cluster.isEmpty())
            .collect(Collectors.toList());
    }
}
