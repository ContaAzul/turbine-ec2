package com.contaazul.turbine.ec2;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.contaazul.turbine.Config;

/**
 * Builds EC2 clients.
 * @author Carlos Alexandro Becker
 */
public interface EC2ClientProvider {
    /**
     * Builds and returns a new amazon ec2 client.
     * @return EC2 client.
     */
    AmazonEC2Client client();

    /**
     * Smart implementation of EC2ClientProvider.
     */
    class Smart implements EC2ClientProvider {
        /**
         * Aws credentials.
         */
        private final transient AWSCredentials credentials;
        /**
         * Config.
         */
        private final transient Config config;

        /**
         * Ctor.
         * @param credentials Aws Credentials.
         * @param config Config.
         */
        public Smart(final AWSCredentials credentials, final Config config) {
            this.credentials = credentials;
            this.config = config;
        }

        @Override
        public AmazonEC2Client client() {
            final AmazonEC2Client client = new AmazonEC2Client(this.credentials);
            client.setRegion(
                Region.getRegion(Regions.fromName(this.config.region()))
            );
            return client;
        }
    }
}
