package com.contaazul.turbine.ec2;

import com.contaazul.turbine.Config;
import com.google.common.base.Strings;

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
    public String get() {
        String tag = this.config.tag(this.cluster);
        if (Strings.isNullOrEmpty(tag)) {
            tag = this.config.defaultTag();
        }
        return String.format("tag:%s", tag);
    }
}
