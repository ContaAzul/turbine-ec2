package com.contaazul.turbine.ec2;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.contaazul.turbine.Config;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test case for EC2ClientProvider.
 * @author Carlos Alexandro Becker
 */
public final class EC2ClientProviderTest {
    /**
     * EC2ClientProvider can build an EC2 client.
     */
    @Test
    public void buildsClient() {
        final AmazonEC2Client client = new EC2ClientProvider.Smart(
            new AWSCredentials() {
                @Override
                public String getAWSAccessKeyId() {
                    return "any";
                }

                @Override
                public String getAWSSecretKey() {
                    return "any";
                }
            },
            new Config() {
                @Override
                public String clusters() {
                    return "";
                }

                @Override
                public String defaultTag() {
                    return "";
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
                public String region() {
                    return "sa-east-1";
                }
            }
        ).client();
        Assertions.assertThat(client).isNotNull();
    }
}
