package com.contaazul;

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

		@Override
		public String clusters() {
			return Config.FromProperties.CLUSTERS.get();
		}
	}
}
