package com.contaazul.turbine.ec2;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.Reservation;
import com.contaazul.turbine.Config;
import com.netflix.turbine.discovery.Instance;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Test case for InstanceList.
 * @author Carlos Alexandro Becker
 */
public final class InstanceListTest {
    @Mock
    private AmazonEC2Client client;
    @Mock
    private EC2ClientProvider provider;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(this.provider.client()).thenReturn(this.client);
    }

    /**
     * InstanceList can list instances.
     */
    @Test
    public void listsInstances() {
        Mockito.when(
            this.client.describeInstances(
                Mockito.any(DescribeInstancesRequest.class)
            )
        ).thenReturn(
            new DescribeInstancesResult()
                .withReservations(
                    Collections.singletonList(this.fakeReservation())
                )
        );

        final List<Instance> instances = new InstanceList(
            "blah",
            this.fakeConfig(),
            this.provider
        ).get();
        Assertions.assertThat(instances).hasSize(1);
    }

    /**
     * InstanceList can list instances.
     */
    @Test
    public void listsInstancesFromMultipleReservartions() {
        Mockito.when(
            this.client.describeInstances(
                Mockito.any(DescribeInstancesRequest.class)
            )
        ).thenReturn(
            new DescribeInstancesResult()
                .withReservations(
                    Arrays.asList(
                        this.fakeReservation(),
                        this.fakeReservation(),
                        this.fakeReservation(),
                        this.fakeReservation()
                    )
                )
        );

        final List<Instance> instances = new InstanceList(
            "blah",
            this.fakeConfig(),
            this.provider
        ).get();
        Assertions.assertThat(instances).hasSize(4);
    }

    private Config fakeConfig() {
        return new Config() {
            @Override
            public String clusters() {
                return "blah";
            }

            @Override
            public String defaultTag() {
                return "name";
            }

            @Override
            public String tag(final String cluster) {
                return "";
            }

            @Override
            public String value(final String cluster) {
                return "";
            }

            @Override
            public String region(final String cluster) {
                return "";
            }

            @Override
            public String region() {
                return "";
            }
        };
    }

    /**
     * Fake reservation.
     * @return Fake reservation with a single instance.
     */
    private Reservation fakeReservation() {
        return new Reservation()
            .withInstances(Collections.singletonList(this.fakeInstance()));
    }

    /**
     * Fake instance.
     * @return Fake running instance.
     */
    private com.amazonaws.services.ec2.model.Instance fakeInstance() {
        return new com.amazonaws.services.ec2.model.Instance()
            .withPrivateDnsName("any")
            .withState(new InstanceState().withName("running"));
    }
}
