package com.micro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.Before;
import org.junit.Test;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.micro.constant.AppConstants.ReplicationStrategy;
import com.micro.cassandra.Cassandra;
import com.micro.cassandra.CassandraConnector;

public class TestCassandra {

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
		CassandraConnector cassandraConnector = new CassandraConnector();
		cassandraConnector.connect("127.0.0.1", 9142);
		this.session = cassandraConnector.getSession();

	}

	//@Test
	public void should_createKeySpace_when_allParameterPassed() {
		String keyspaceName = "test";

		int replicationFactor = 1;

		Cassandra.createKeySpace(this.session, keyspaceName, ReplicationStrategy.SimpleStrategy, replicationFactor);

		ResultSet result = session.execute("SELECT * FROM system_schema.keyspaces;");
		List<String> matchedKeyspaces = result.all().stream().filter(r -> {
			Map<String, String> replication = r.getMap(2, String.class, String.class);
			return r.getString(0).equals(keyspaceName.toLowerCase())
					&& replication.get("class").contains(ReplicationStrategy.SimpleStrategy.toString())
					&& Integer.parseInt(replication.get("replication_factor")) == replicationFactor;
		}).map(r -> r.getString(0)).collect(Collectors.toList());
		assertEquals(matchedKeyspaces.size(), 1);
	}

	//@Test
	public void should_throwQueryValidationException_when_keyspaceIsNull() {
		String keyspaceName = null;
		int replicationFactor = -1;
		try {
			Cassandra.createKeySpace(this.session, keyspaceName, ReplicationStrategy.SimpleStrategy, replicationFactor);
		} catch (QueryValidationException e) {
			assertEquals(e instanceof QueryValidationException, true);
		}
	}

	//@Test
	public void should_throwQueryValidationException_when_keyspaceContainsSpace() {
		String keyspaceName = null;
		int replicationFactor = -1;
		try {
			Cassandra.createKeySpace(this.session, keyspaceName, ReplicationStrategy.SimpleStrategy, replicationFactor);
		} catch (QueryValidationException e) {
			assertEquals(e instanceof QueryValidationException, true);
		}
	}

	//@Test
	public void should_throwQueryValidationException_when_replicationFactorLessThanZero() {
		String keyspaceName = "test";
		int replicationFactor = -1;
		try {
			Cassandra.createKeySpace(this.session, keyspaceName, ReplicationStrategy.SimpleStrategy, replicationFactor);
		} catch (QueryValidationException e) {
			assertEquals(e instanceof QueryValidationException, true);
		}
	}

	//@Test
	public void should_createTable_whenProperParameterIsPassed() {
		String keySpace = "test";
		String tableName = "Machine";

		Map<String, String> columConfiguration = new HashMap<>();
		columConfiguration.put("id ", "UUID PRIMARY KEY");
		columConfiguration.put("lastname ", "text");
		columConfiguration.put("birthday ", "timestamp");
		Cassandra.createKeySpace(this.session, keySpace, ReplicationStrategy.SimpleStrategy, 1);
		Cassandra.createTable(this.session, keySpace, tableName, columConfiguration);

		ResultSet result = session.execute("SELECT * FROM " + keySpace + "." + tableName + ";");

		List<String> columnNames = result.getColumnDefinitions().asList().stream().map(cl -> cl.getName())
				.collect(Collectors.toList());

		assertEquals(columnNames.size(), 3);
		assertTrue(columnNames.contains("id"));
		assertTrue(columnNames.contains("lastname"));
		assertTrue(columnNames.contains("birthday"));
	}

	//@Test
	public void should_AddNewColumn_whenProperParameterPassed() {
		String keySpace = "test";
		String tableName = "Mac";

		Map<String, String> columConfiguration = new HashMap<>();
		columConfiguration.put("id ", "UUID PRIMARY KEY");
		columConfiguration.put("lastname ", "text");
		columConfiguration.put("birthday ", "timestamp");
		Cassandra.createKeySpace(this.session, keySpace, ReplicationStrategy.SimpleStrategy, 1);
		Cassandra.createTable(this.session, keySpace, tableName, columConfiguration);
		Cassandra.alterTable(session, keySpace, tableName, "firstName", "text");
		ResultSet result = session.execute("SELECT * FROM " + keySpace + "." + tableName + ";");

		List<String> columnNames = result.getColumnDefinitions().asList().stream().map(cl -> cl.getName())
				.collect(Collectors.toList());

		assertTrue(columnNames.contains("firstname"));

	}
	
	
	public void should_Create_Type_whenProperParameterPassesd() {
		
	}
}
