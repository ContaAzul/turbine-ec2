package com.contaazul.turbine.ec2;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test case for AWSCredentialFromProperties.
 * @author Carlos Alexandro Becker
 */
public final class AWSCredentialFromPropertiesTest {
    /**
     * AWSCredentialFromProperties can get data from our
     * src/test/resources/config.properties.
     */
    @Test
    public void getsFromConfig() {
        final AWSCredentialsFromProperties config =
            new AWSCredentialsFromProperties();
        Assertions.assertThat(config)
            .matches(cfg -> cfg.getAWSAccessKeyId().equals("access-key"))
            .matches(cfg -> cfg.getAWSSecretKey().equals("secret-key"));
    }
}
