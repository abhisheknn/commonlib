package com.micro.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.ConfigException;

public class KafkaConsumer {

	public  Builder builder;
	private Consumer consumer;
	private String topic ;
	private ConsumerProcessor processor;
	
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	
	public void setTopic(String topic) {
		this.topic=topic;
	}
	
	public void setProcessor(ConsumerProcessor processor) {
		this.processor=processor;
	}
	public  Builder build() {
		builder= new Builder();
		return builder;
	}
	
	public void consume() {
		this.consumer.subscribe(Arrays.asList(this.topic));
		new Thread(()-> {
			while(true) {
				this.processor.execute();
			}
		},"cons").start();
	}
	
	public class Builder {
	 KafkaConsumer kConsumer= new KafkaConsumer();
	 Consumer consumer;
	
	 private void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	 }
	 
	 

	public Builder withConfig(Properties props) {
		if(null==props.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG) 
				|| null == props.get(ConsumerConfig.GROUP_ID_CONFIG))
			throw new ConfigException(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG + "or" + ConsumerConfig.GROUP_ID_CONFIG +"not present");	
		if(null==props.get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG))  props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		if(null==props.get(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG))  props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		if(null==props.get(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG))  props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		if(null==props.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG))   props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		if(null==props.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG)) props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		if(null==props.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG)) props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
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
	 
	public String getTopic() {
		return kConsumer.getTopic();
	} 
	 
	public KafkaConsumer withProcessor(ConsumerProcessor processor ) {
		kConsumer.setProcessor(processor);
		return kConsumer;
	} 
	 
}
	
}

