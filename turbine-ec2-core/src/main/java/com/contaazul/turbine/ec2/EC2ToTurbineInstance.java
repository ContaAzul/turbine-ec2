package com.contaazul.turbine.ec2;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceStateName;

/**
 * Converts ec2 instances to turbine instances.
 */
public final class EC2ToTurbineInstance {
    /**
     * Cluster name.
     */
    private final transient String cluster;

    /**
     * Ctor.
     * @param cluster Cluster.
     */
    public EC2ToTurbineInstance(String cluster) {
        this.cluster = cluster;
    }

    /**
     * Converts the ec2 instance to a turbine instance.
     * @param ec2 EC2 instance.
     * @return Turbine instance.
     */
    public com.netflix.turbine.discovery.Instance convert(final Instance ec2) {
        final String dns = ec2.getPrivateIpAddress();
        final boolean state = InstanceStateName.fromValue(
            ec2.getState().getName()
        ) == InstanceStateName.Running;
        return new com.netflix.turbine.discovery.Instance(
            dns, this.cluster, state
        );
    }
}
