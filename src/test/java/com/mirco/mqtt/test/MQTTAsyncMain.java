package com.mirco.mqtt.test;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.mirco.mqtt.MQTTAsync;

public class MQTTAsyncMain {
	
//	public static void main(String[] args) throws MqttException {
//		String publisherId = UUID.randomUUID().toString();
//		String subscriberId = UUID.randomUUID().toString();
//		MqttConnectOptions options = new MqttConnectOptions();
//		options.setAutomaticReconnect(true);
//		options.setCleanSession(false);
//		options.setConnectionTimeout(10);
//		
//		MQTTAsync.getClient("tcp://HOST:1883",publisherId).ifPresent((publisher)->{
//			try {
//				connectPublisher(options, publisher);
//			} catch (MqttSecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (MqttException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//		});
//		
//		
//		
//		MQTTAsync.getClient("tcp://HOST:1883",subscriberId).ifPresent((subscriber)->{
//			try {
//				connectSubscriber(options, subscriber);
//			} catch (MqttSecurityException e) {
//				e.printStackTrace();
//			} catch (MqttException e) {
//				e.printStackTrace();
//			}	
//		});
//	}
//
//	private static void connectPublisher(MqttConnectOptions options, IMqttAsyncClient publisher)
//			throws MqttException, MqttSecurityException {
//		IMqttActionListener actionListener=new IMqttActionListener() {
//		    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//		        System.out.println("failed to connect " + exception);
//		        System.exit(2);
//		    }
//
//		    public void onSuccess(IMqttToken asyncActionToken) {
//		    	   byte[] payload = "test".getBytes();       
//		    	   MqttMessage msg =  new MqttMessage(payload); 
//		    	   msg.setQos(0);
//		    	   msg.setRetained(true);
//		    	   MQTTAsync.publish(publisher,"test/topic",msg);
//		    	   System.out.println("Connection successful" + asyncActionToken);
//		    }
//		} ;
//	
//		MQTTAsync.connect(options, publisher, null, actionListener);
//	}
//	
//	
//	private static void connectSubscriber(MqttConnectOptions options, IMqttAsyncClient subscriber)
//			throws MqttException, MqttSecurityException {
//		IMqttActionListener actionListener=new IMqttActionListener() {
//            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                System.out.println("failed to connect " + exception);
//                System.exit(2);
//            }
//
//            public void onSuccess(IMqttToken asyncActionToken) {
//            	MQTTAsync.subscribe(subscriber,"test/topic",2,(m,msg)->{
//            		System.out.println(new String(msg.getPayload()));
//            	}); 
//            	System.out.println("Connection successful Sub" + asyncActionToken);
//            }
//		};
//	
//		MQTTAsync.connect(options, subscriber, null, actionListener);
//	}
}
