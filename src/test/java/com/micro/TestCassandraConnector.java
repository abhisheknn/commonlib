package com.micro;

import java.io.IOException;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.Before;
import org.junit.Test;

import com.datastax.driver.core.Session;
import com.micro.cassandra.CassandraConnector;

public class TestCassandraConnector {

	private Session session;
	
@Before
public void connectCassandra() {
	try {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra(50000L);
	} catch (ConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TTransportException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	CassandraConnector cassandraConnector= new CassandraConnector();
	cassandraConnector.connect("127.0.0.1", 9142);
	this.session=cassandraConnector.getSession();
	
}	
	
@Test 
public void should_createSession_when_getSessionCalled() {
}
	
}
