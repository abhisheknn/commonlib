package com.micro.cassandra;

import java.util.Map;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.micro.constant.AppConstants.ReplicationStrategy;


public class Cassandra { 
	
	public static class Configuration{
		String name;
		String keySpace;
		Map<String, String> conf;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Map<String, String> getConf() {
			return conf;
		}
		public void setConf(Map<String, String> conf) {
			this.conf = conf;
		}
		public String getKeySpace() {
			return keySpace;
		}
		public void setKeySpace(String keySpace) {
			this.keySpace = keySpace;
		}
		
	}
	
	
	public static enum CONFIGURATION_TYPE{
		TABLE,
		TYPE,
		KEYSPACE
	}
	 
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
	
	public static void createType(Session session ,String keySpace,String type,Map<String,String> configuration) {

	    StringBuilder sb = new StringBuilder("CREATE TYPE IF NOT EXISTS ")
	    					.append(keySpace)
	    					.append(".")
	    					.append(type).append("(");
	    					
	    					for(Map.Entry<String, String> column:configuration.entrySet()) {
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
	
	
	public static void insertJSON(Session session, String keySpace,String tableName,String data ) {
	    
		if(session!=null) {
			StringBuilder sb = new StringBuilder("INSERT INTO ")
		      .append(keySpace)
		      .append(".")
		      .append(tableName).append(" JSON ")
		      .append("'")
		      .append(data)
		      .append("'")
		      .append(";");
		 
		    String query = sb.toString();
		    session.execute(query);
		}
	}
	
	
	public static ResultSet select(Session session, String keySpace,String table,String valuestoFetch,String whereClause) {
		ResultSet result = null;
		StringBuilder query= new StringBuilder();
		query.append("select ")
		.append(valuestoFetch)
		.append(" from ")
		.append(keySpace)
		.append(".")
		.append(table)
		.append(" where ")
		.append(whereClause);
		if(session!=null) {
	    	 result =  session.execute(query.toString());
		 }
		return result;
		}
	

	public static void update(Session session, String keySpace,String tableName,Map<String,Object> values,String whereClause) {
		ResultSet result = null;
		StringBuilder query= new StringBuilder();
		query.append("UPDATE ")
		.append(keySpace)
		.append(".")
		.append(tableName)
		.append(" SET ");
		for(Map.Entry<String,Object> value:values.entrySet()) {
		query.append(value.getKey());
		Object val=value.getValue();
		query.append("=");
		if(val instanceof String) {
			query.append("'").append(val).append("'");
		} else if(val instanceof Integer) {
			query.append(val);
		}
		query.append(",");
		}
		int index=query.lastIndexOf(",");
		query.replace(index, index, "");
		query.append(" WHERE ")
		.append(whereClause);
		
		
	}
	
	public static ResultSet update(Session session, String keySpace,String tableName,String values,String whereClause) {
		ResultSet result = null;
		StringBuilder query= new StringBuilder();
		query.append("UPDATE ")
		.append(keySpace)
		.append(".")
		.append(tableName)
		.append(" SET ")
		.append(values)
		.append(" WHERE ")
		.append(whereClause);
		
		if(session!=null) {
	    	 result =  session.execute(query.toString());
		 }
		return result;
	}
	
}
