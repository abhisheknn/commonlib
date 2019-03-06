package com.micro.kafka;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.config.ConfigException;

public abstract class ConsumerThread implements Runnable {
	protected final KafkaConsumer<String, String> consumer;
	private final String topic;

	public ConsumerThread(Properties props, String topic) {
		Properties prop = createConsumerConfig(props);
		this.consumer = new KafkaConsumer<>(prop);
		this.topic = topic;
		this.consumer.subscribe(Arrays.asList(this.topic));
	}

	private static Properties createConsumerConfig(Properties props) {
		//Properties props = new Properties();
		//props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
		//props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		if(null!=props.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG) || null != props.get(ConsumerConfig.GROUP_ID_CONFIG)) throw new ConfigException(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG + "or" + ConsumerConfig.GROUP_ID_CONFIG +"not present");	
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		return props;

	}
}