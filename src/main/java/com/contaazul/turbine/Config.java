package com.contaazul.turbine;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.turbine.discovery.InstanceDiscovery;

/**
 * Auto discovery configuration.
 *
 * @author Carlos Alexandro Becker
 */
public interface Config {
	/**
	 * Get the list of clusters configured by the user.
	 * @return Cluster config.
	 */
	String clusters();

	String defaultTag();

	String tag(final String cluster);

	/**
	 * Loads config from properties.
	 */
	final class FromProperties implements Config {
		/**
		 * Cluster list property.
		 */
		private static final DynamicStringProperty CLUSTERS =
			DynamicPropertyFactory.getInstance().getStringProperty(
				InstanceDiscovery.TURBINE_AGGREGATOR_CLUSTER_CONFIG, null
			);

		/**
		 * Default tag name.
		 */
		private static final DynamicStringProperty DEFAULT_TAG =
			DynamicPropertyFactory
				.getInstance()
				.getStringProperty("turbine.ec2.default.tag", null);

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
				"turbine.ec2.%s.tag", cluster
			);
			return DynamicPropertyFactory.getInstance()
				.getStringProperty(property, null)
				.get();
		}
	}
}
