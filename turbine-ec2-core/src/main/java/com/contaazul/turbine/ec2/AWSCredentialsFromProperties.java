package com.contaazul.turbine.ec2;

import com.amazonaws.auth.AWSCredentials;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

/**
 * AWSCredentials from system properties.
 * @author Carlos Alexandro Becker
 */
public final class AWSCredentialsFromProperties implements AWSCredentials {
    /**
     * Access key.
     */
    private static final DynamicStringProperty ACCESS =
        DynamicPropertyFactory
            .getInstance()
            .getStringProperty("turbine.ec2.aws.access", null);
    /**
     * Secret key.
     */
    private static final DynamicStringProperty SECRET =
        DynamicPropertyFactory
            .getInstance()
            .getStringProperty("turbine.ec2.aws.secret", null);

    @Override
    public String getAWSAccessKeyId() {
        return AWSCredentialsFromProperties.ACCESS.get();
    }

    @Override
    public String getAWSSecretKey() {
        return AWSCredentialsFromProperties.SECRET.get();
    }
}
