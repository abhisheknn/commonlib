package com.mirco.mqtt.test;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTMain {
//	public static void main(String[] args) {
//		String publisherId = UUID.randomUUID().toString();
//		String subscriberId = UUID.randomUUID().toString();
//		try {
//			IMqttClient publisher = new MqttClient("tcp://HOST:1883",publisherId);
//			IMqttClient subscriber = new MqttClient("tcp://HOST:1883",subscriberId);
//			MqttConnectOptions options = new MqttConnectOptions();
//			options.setAutomaticReconnect(true);
//			options.setCleanSession(false);
//			options.setConnectionTimeout(10);
//			publisher.connect(options);
//			subscriber.connect(options);
//			byte[] payload = "test".getBytes();       
//		    MqttMessage msg =  new MqttMessage(payload); 
//		    msg.setQos(0);
//		    msg.setRetained(true);
//		    publisher.publish("test/topic",msg);
//		
//		    
//		    CountDownLatch receivedSignal = new CountDownLatch(10);
//		    subscriber.subscribe("test/topic", (topic, msgFromMQTT) -> {
//		        byte[] payloadFromMQTT = msg.getPayload();
//		        System.out.println(new String(payloadFromMQTT));
//		        receivedSignal.countDown();
//		    });    
//		    try {
//				receivedSignal.await(1, TimeUnit.MINUTES);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		    
//		    
//		} catch (MqttException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
