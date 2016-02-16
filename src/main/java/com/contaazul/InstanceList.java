package com.contaazul;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Reservation;
import com.netflix.turbine.discovery.Instance;

import java.util.ArrayList;
import java.util.List;

public final class InstanceList {
	private final String cluster;

	public InstanceList(final String cluster) {
		this.cluster = cluster;
	}

	public List<Instance> get() {
		final List<Instance> list = new ArrayList<Instance>();
		final EC2ToTurbineInstance converter = new EC2ToTurbineInstance( this.cluster );
		for (final String tag : System.getProperty( "TAG_NAMES" ).split( ";" )) {
			for (Reservation reservation : filter( tag ).getReservations()) {
				for (com.amazonaws.services.ec2.model.Instance instance : reservation.getInstances()) {
					list.add( converter.convert( instance ) );
				}
			}
		}
		return list;
	}

	private DescribeInstancesResult filter(final String tag) {
		final Filter filter = new Filter()
				.withName( String.format( "tag:%s", tag ) )
				.withValues( this.cluster );
		return new AmazonEC2Client( new Credentials() )
				.describeInstances( new DescribeInstancesRequest().withFilters( filter ) );
	}

	private static class Credentials implements AWSCredentials {
		public String getAWSSecretKey() {
			return System.getProperty( "AWS_SECRET_KEY" );
		}

		public String getAWSAccessKeyId() {
			return System.getProperty( "AWS_ACCESS_KEY" );
		}
	}
}
