package com.contaazul.turbine.ec2;

import com.contaazul.turbine.Config;
import com.google.common.collect.Lists;
import java.util.List;

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
        final String clusterConfig = this.config.clusters();
        if (clusterConfig == null || clusterConfig.trim().length() == 0) {
            return Lists.newArrayList("default");
        }
        return Lists.newArrayList(clusterConfig.trim().split(","));
    }
}
