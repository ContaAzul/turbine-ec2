package com.contaazul.turbine.ec2;

import com.contaazul.turbine.Config;

/**
 * Gets the appropriate tag for a given cluster.
 * @author Carlos Alexandro Becker
 */
public final class ClusterTag {

    /**
     * Cluster name.
     */
    private final transient String cluster;

    /**
     * Config.
     */
    private final transient Config config;

    /**
     * Ctor.
     * @param cluster Cluster name.
     * @param config Config.
     */
    public ClusterTag(final String cluster, final Config config) {
        this.cluster = cluster;
        this.config = config;
    }

    /**
     * Gets the adequate tag from config.
     * @return Tag.
     */
    public String name() {
        String tag = this.config.tag(this.cluster);
        if (tag.isEmpty()) {
            tag = this.config.defaultTag();
        }
        if (tag.isEmpty()) {
            throw new RuntimeException(
                String.format(
                    "No tags specified for '%s'",
                    this.cluster
                )
            );
        }
        return String.format("tag:%s", tag);
    }

    /**
     * Gets the adequate tag value from config. Defaults to cluster name.
     * @return Tag.
     */
    public String value() {
        String value = this.config.value(this.cluster);
        if (value.isEmpty()) {
            value = this.cluster;
        }
        return value;
    }
}
