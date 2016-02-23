package com.contaazul.turbine;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.turbine.discovery.InstanceDiscovery;

/**
 * Auto discovery configuration.
 * @author Carlos Alexandro Becker
 */
public interface Config {
    /**
     * Get the list of clusters configured by the user.
     * @return Cluster config.
     */
    String clusters();

    /**
     * Default tag to filter on.
     * @return Default tag name.
     */
    String defaultTag();

    /**
     * Gets the tag name for a given cluster.
     * @param cluster Cluster name.
     * @return Tag name.
     */
    String tag(String cluster);

    /**
     * Gets the tag value for a given cluster.
     * @param cluster Cluster name.
     * @return Tag value.
     */
    String value(String cluster);

    /**
     * Gets the region in which the app is running.
     * @param cluster Cluster name.
     * @return Region of the cluster or default.
     */
    String region(String cluster);

    /**
     * Gets the default region in which the app is running.
     * @return Default region.
     */
    String region();

    /**
     * Loads config from properties.
     */
    final class FromProperties implements Config {
        /**
         * Cluster list property.
         */
        private static final DynamicStringProperty CLUSTERS =
            DynamicPropertyFactory.getInstance().getStringProperty(
                InstanceDiscovery.TURBINE_AGGREGATOR_CLUSTER_CONFIG, ""
            );

        /**
         * Default tag name.
         */
        private static final DynamicStringProperty DEFAULT_TAG =
            DynamicPropertyFactory
                .getInstance()
                .getStringProperty("turbine.ec2.tag", "");

        /**
         * Default region.
         */
        private static final DynamicStringProperty DEFAULT_REGION =
            DynamicPropertyFactory
                .getInstance()
                .getStringProperty("turbine.ec2.aws.region", "us-east-1");

        @Override
        public String clusters() {
            return Config.FromProperties.CLUSTERS.get();
        }

        @Override
        public String defaultTag() {
            return Config.FromProperties.DEFAULT_TAG.get();
        }

        @Override
        public String tag(final String cluster) {
            final String property = String.format(
                "turbine.ec2.tag.%s", cluster
            );
            return DynamicPropertyFactory.getInstance()
                .getStringProperty(property, "")
                .get();
        }

        @Override
        public String value(final String cluster) {
            final String property = String.format(
                "turbine.ec2.value.%s", cluster
            );
            return DynamicPropertyFactory.getInstance()
                .getStringProperty(property, "")
                .get();
        }

        @Override
        public String region(final String cluster) {
            final String property = String.format(
                "turbine.ec2.aws.region.%s", cluster
            );
            return DynamicPropertyFactory.getInstance()
                .getStringProperty(property, "")
                .get();
        }

        @Override
        public String region() {
            return Config.FromProperties.DEFAULT_REGION.get();
        }
    }
}
