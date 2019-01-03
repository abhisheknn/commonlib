package com.micro.cassandra;

import java.util.Map;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.micro.constant.AppConstants.ReplicationStrategy;


public class Cassandra { 
	 
	public static void createKeySpace(Session session,String keySpaceName, ReplicationStrategy replicationStrategy, int replicationFactor) throws QueryValidationException {
		
		if(session==null) {
			throw new QueryValidationException("Session cannnot be null") {};
		}
		if(keySpaceName==null) {
			throw new QueryValidationException("keySpaceName should not be null") {}; 
		}
		if(replicationFactor<0) {
			throw new QueryValidationException("Replication factor must be non-negative") {}; 
		}
		
		StringBuilder sb = 
				    new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
				      .append(keySpaceName).append(" WITH replication = {")
				      .append("'class':'").append(replicationStrategy.toString())
				      .append("','replication_factor':").append(replicationFactor)
				      .append("};");
				         
				    String query = sb.toString();
				    session.execute(query);
	}
	
	 
	public static void createTable(Session session ,String keySpace,String tableName,Map<String,String> columConfiguration) {

    StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
    					.append(keySpace)
    					.append(".")
    					.append(tableName).append("(");
    					
    					for(Map.Entry<String, String> column:columConfiguration.entrySet()) {
    						String key=column.getKey();
    						String value=column.getValue();
    						sb.append(key+ " "+ value+",");
    					}
    					int lastIndex=sb.lastIndexOf(",");
    					sb.replace(lastIndex, lastIndex, " ");
    					sb.append(");");
    					
    String query = sb.toString();
    session.execute(query);
	}
	
	
	public static void alterTable(Session session, String keySpace,String tableName,String columnName, String columnType) {
	    
	 if(session!=null) {
		StringBuilder sb = new StringBuilder("ALTER TABLE ")
	      .append(keySpace)
	      .append(".")
	      .append(tableName).append(" ADD ")
	      .append(columnName).append(" ")
	      .append(columnType).append(";");
	 
	    String query = sb.toString();
	    session.execute(query);
	 }
	}
	
}
