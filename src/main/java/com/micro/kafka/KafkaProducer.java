package com.micro.kafka;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.serialization.StringSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class KafkaProducer{
	private Producer kafkaProducer;
	public static Builder builder;
	private Properties config;
	
	private Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	private Type  mapType= new TypeToken<Map<String,Object>>(){}.getType();
	private Type listType= new TypeToken<List<Map<String,Object>>>(){}.getType();
	
	
	public static Builder build() {
		return new  Builder();
	}
	
	
	private void setKafkaProducer(Producer kafkaProducer) {
		this.kafkaProducer=kafkaProducer;
	}
	
	public void produce(String topic,String key, Map<String, Object> requestBody) {
		
		ProducerRecord<String, String> record= new ProducerRecord<String, String>(topic,key, gson.toJson(requestBody));
		kafkaProducer.send(record);
	}
	
	public static class Builder {
		private KafkaProducer kafkaProducer = new KafkaProducer();
		
		public KafkaProducer withConfig(Properties config) {
			if(null==config.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)) throw new ConfigException(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG + "not present");
			config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			org.apache.kafka.clients.producer.KafkaProducer producer= new org.apache.kafka.clients.producer.KafkaProducer<>(config);
			kafkaProducer.setKafkaProducer(producer);	
			return kafkaProducer;
		}
	} ;
}
