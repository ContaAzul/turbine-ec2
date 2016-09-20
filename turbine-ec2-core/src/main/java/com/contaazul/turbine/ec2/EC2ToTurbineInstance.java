package com.contaazul.turbine.ec2;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceStateName;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;

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
        final String addr = this.address(ec2);
        final boolean state = InstanceStateName.fromValue(
            ec2.getState().getName()
        ) == InstanceStateName.Running;
        return new com.netflix.turbine.discovery.Instance(
            addr, this.cluster, state
        );
    }

    /**
     * Get the address of a given ec2 instance.
     * @param ec2 Instance
     * @return Either private IP or Name (if accessible)
     */
    private String address(Instance ec2) {
        final Optional<String> name = ec2.getTags().stream()
                .filter(tag -> tag.getKey().equals("Name"))
                .map(tag -> tag.getValue())
                .findFirst();

        if (name.isPresent()) {
            final String host = name.get();
            // check if port 80 or 8080 are up
            if (ping(host, 80) || ping(host, 8080)) {
                return host;
            }
        }
        return ec2.getPrivateIpAddress();
    }

    /**
     * Ping a host on a port.
     * @param host Host
     * @param port Port
     * @return
     */
    public boolean ping(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 100);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
