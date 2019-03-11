package com.micro.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;

public class CassandraConnector {

	private Cluster cluster;
	private Session session;
	
	public void connect (String nodes[], Integer port){
		Builder builder=Cluster.builder().addContactPoints(nodes);
		if(port!=null) {
			builder.withPort(port);
		}
		cluster=builder.build();
		session=cluster.connect();
	}
	
	public Session getSession() {
		return this.session;
	}
	public void close() {
		session.close();
		cluster.close();
	}
}
