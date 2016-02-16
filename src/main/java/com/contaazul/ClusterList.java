package com.contaazul;

import com.google.common.collect.Lists;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.turbine.discovery.InstanceDiscovery;

import java.util.List;

public final class ClusterList {
	private static final DynamicStringProperty CLUSTER_LIST = DynamicPropertyFactory.getInstance()
			.getStringProperty( InstanceDiscovery.TURBINE_AGGREGATOR_CLUSTER_CONFIG, null );

	public List<String> get() throws Exception {
		final String clusterConfig = CLUSTER_LIST.get();
		if (clusterConfig == null || clusterConfig.trim().length() == 0) {
			return Lists.newArrayList( "default" );
		}
		final List<String> clusters = Lists.newArrayList( clusterConfig.trim().split( "," ) );
		if (clusters.size() == 0) {
			throw new Exception( "Must configure property: " + CLUSTER_LIST.getName() );
		}
		return clusters;
	}
}
