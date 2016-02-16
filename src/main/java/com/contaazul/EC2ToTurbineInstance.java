package com.contaazul;

import com.amazonaws.services.ec2.model.Instance;

public final class EC2ToTurbineInstance {
	private final String cluster;

	public EC2ToTurbineInstance(String cluster) {
		this.cluster = cluster;
	}

	public com.netflix.turbine.discovery.Instance get(final Instance ec2) {
		final String dns = ec2.getPrivateDnsName();
		final boolean state = ec2.getState().getName().equals( "running" );
		return new com.netflix.turbine.discovery.Instance( dns, this.cluster, state );
	}
}
