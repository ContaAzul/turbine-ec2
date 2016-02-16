package com.contaazul.turbine.ec2;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.InstanceStateName;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test case for {@link EC2ToTurbineInstance}.
 * @author Carlos Alexandro Becker
 */
public final class EC2ToTurbineInstanceTest {
    /**
     * {@link EC2ToTurbineInstance} can convert a running ec2 instance into
     * a turbine instance.
     */
    @Test
    public void convertsRunningInstances() {
        final String dns = "blah.dev.contaazul";
        final String cluster = "blah";
        final Instance ec2 = new Instance()
            .withPrivateDnsName(dns)
            .withState(new InstanceState().withName(InstanceStateName.Running));
        Assertions.assertThat(new EC2ToTurbineInstance(cluster).convert(ec2))
            .isNotNull()
            .matches(instance -> instance.getHostname().equals(dns))
            .matches(com.netflix.turbine.discovery.Instance::isUp);
    }

    /**
     * {@link EC2ToTurbineInstance} can convert a non running ec2 instance into
     * a turbine instance.
     */
    @Test
    public void convertsNonRunningInstances() {
        final String dns = "blah2.dev.contaazul";
        final String cluster = "blah";
        final Instance ec2 = new Instance()
            .withPrivateDnsName(dns)
            .withState(new InstanceState().withName(InstanceStateName.Pending));
        Assertions.assertThat(new EC2ToTurbineInstance(cluster).convert(ec2))
            .isNotNull()
            .matches(instance -> instance.getHostname().equals(dns))
            .matches(instance -> !instance.isUp());
    }
}
