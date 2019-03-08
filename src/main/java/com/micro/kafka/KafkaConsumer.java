package com.micro.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.ConfigException;

public class KafkaConsumer {

	public static Builder builder;
	private Consumer consumer;
	private String topic ;
	private Processor processor;
	
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	
	public void setTopic(String topic) {
		this.topic=topic;
	}
	
	public void setProcessor(Processor processor) {
		this.processor=processor;
	}
	public static Builder build() {
		builder= new Builder();
		return builder;
	}
	
	public void consume() {
		this.consumer.subscribe(Arrays.asList(this.topic));
		new Thread(()-> {
			while(true) {
			this.processor.execute();
			}
		}).start();
	}
	
	public static class Builder {
	 KafkaConsumer kConsumer= new KafkaConsumer();
	 Consumer consumer;
	
	 public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	 }

	public Builder withConfig(Properties props) {
		if(null==props.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG) || null == props.get(ConsumerConfig.GROUP_ID_CONFIG)) throw new ConfigException(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG + "or" + ConsumerConfig.GROUP_ID_CONFIG +"not present");	
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		org.apache.kafka.clients.consumer.KafkaConsumer consumer= new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
		this.setConsumer(consumer);
		kConsumer.setConsumer(consumer);
		return builder;
	}
	 
	 public Consumer getConsumer( ) {
		 return consumer;
	 }
	
	 public Builder withTopic(String topic) {
		kConsumer.setTopic(topic);
		return builder;
	} 
	 
	public KafkaConsumer withProcessor(Processor processor ) {
		kConsumer.setProcessor(processor);
		return kConsumer;
	} 
	 
}
	
}

