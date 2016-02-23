package com.contaazul.turbine;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.Reservation;
import com.contaazul.turbine.ec2.EC2ClientProvider;
import java.util.Arrays;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Test case for EC2Discovery.
 * @author Carlos Alexandro Becker
 */
public final class EC2DiscoveryTest {
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private Config config;
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private DescribeInstancesResult result;
    @Mock
    private AmazonEC2Client client;
    @Mock
    private EC2ClientProvider provider;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(this.config.clusters()).thenReturn("svc1");
        Mockito.when(this.config.defaultTag()).thenReturn("tag");
        Mockito.when(this.provider.client()).thenReturn(this.client);
        Mockito.when(
            this.client.describeInstances(
                Mockito.any(DescribeInstancesRequest.class)
            )
        ).thenReturn(this.result);
}

    /**
     * EC2Discovery can return an empty list of instances.
     * @throws Exception in case of error.
     */
    @Test
    public void returnsEmptyList() throws Exception {
        Assertions.assertThat(
            new EC2Discovery(this.config, this.provider).getInstanceList()
        ).hasSize(0);
    }

    /**
     * EC2Discovery can return a list of instances filtered through multiple
     * reservations.
     * @throws Exception in case of error.
     */
    @Test
    public void returnsInstances() throws Exception {
        Mockito.when(this.result.getReservations()).thenReturn(
            Arrays.asList(
                this.fakeReservation(),
                this.fakeReservation(),
                this.fakeReservation()
            )
        );
        Assertions.assertThat(
            new EC2Discovery(this.config, this.provider).getInstanceList()
        ).hasSize(3);
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
    private Instance fakeInstance() {
        return new Instance()
            .withPrivateIpAddress(String.valueOf(System.currentTimeMillis()))
            .withPrivateDnsName("any")
            .withState(new InstanceState().withName("running"));
    }
}
