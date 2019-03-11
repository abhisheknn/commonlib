package com.micro.redis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.support.ConnectionPoolSupport;

/*
 * Provide the redis connection pool 
 * 
 * Usage :
 * try (StatefulRedisConnection<String, String> connection = pool.borrowObject()) {
    RedisCommands<String, String> commands = connection.sync();
    commands.multi();
    commands.set("key", "value");
    commands.set("key2", "value2");
    commands.exec();
	}
 * 
 * 
 * 
 * */
public class RedisConnection {
	private RedisClient client =null;
	private GenericObjectPool<StatefulRedisConnection<String, String>> pool=null;
@PostConstruct 
public void init() {
	client = RedisClient.create(RedisURI.create(System.getenv("REDISHOST"),Integer.parseInt(System.getenv("REDISPORT")))); // get this from config server or kuberentes
	pool   = ConnectionPoolSupport
	        .createGenericObjectPool(() -> client.connect(), new GenericObjectPoolConfig());
}

public GenericObjectPool<StatefulRedisConnection<String, String>> getPool() {
	return pool;
}
public void setPool(GenericObjectPool<StatefulRedisConnection<String, String>> pool) {
	this.pool = pool;
}

@PreDestroy
public void destroy() {
	pool.close();
	client.shutdown();
}

}