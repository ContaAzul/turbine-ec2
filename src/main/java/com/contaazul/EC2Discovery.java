package com.contaazul;

import com.netflix.turbine.discovery.Instance;
import com.netflix.turbine.discovery.InstanceDiscovery;

import java.util.Collection;
import java.util.stream.Collectors;

public final class EC2Discovery implements InstanceDiscovery {
	public Collection<Instance> getInstanceList() throws Exception {
		return new ClusterList().get().parallelStream()
				.map( cluster -> new InstanceList( cluster ).get() )
				.flatMap( list -> list.stream() )
				.collect( Collectors.toList() );
	}
}
