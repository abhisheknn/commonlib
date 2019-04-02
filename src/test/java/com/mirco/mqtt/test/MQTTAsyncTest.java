package com.mirco.mqtt.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mirco.mqtt.MQTTAsync;

public class MQTTAsyncTest {

	MqttConnectOptions options ;
	String publisherId ;
	String subscriberId;
	

	//@Before
	public void setUp() throws Exception {
		publisherId = UUID.randomUUID().toString();
		subscriberId = UUID.randomUUID().toString();
		options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(false);
		options.setConnectionTimeout(10);
		
	}

	//@Test
	public void testPublishAndSubSubscribe() {

		MQTTAsync.getClient("tcp://HOST:1883",publisherId).ifPresent((publisher)->{
			try {
				connectPublisher(options, publisher,"test");
			} catch (MqttSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		});
	
		
		List<String> result = new ArrayList<>();
		MQTTAsync.getClient("tcp://HOST:1883",subscriberId)
		.ifPresent((subscriber)->{
			try {
				
				connectSubscriber(options, subscriber,result);
				//Assert.assertEquals("test", result.get(0));
			} catch (MqttSecurityException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
			}	
		});
		
	}

	private  void connectPublisher(MqttConnectOptions options, IMqttAsyncClient publisher ,String payloads)
			throws MqttException, MqttSecurityException {
		IMqttActionListener actionListener=new IMqttActionListener() {
		    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
		        System.out.println("failed to connect " + exception);
		        System.exit(2);
		    }

		    public void onSuccess(IMqttToken asyncActionToken) {
		    	   byte[] payload = payloads.getBytes();       
		    	   MqttMessage msg =  new MqttMessage(payload); 
		    	   msg.setQos(0);
		    	   msg.setRetained(true);
		    	   MQTTAsync.publish(publisher,"test/topic",msg);
		    	   System.out.println("Connection successful" + asyncActionToken);
		    }
		} ;
	
		MQTTAsync.connect(options, publisher, null, actionListener);
	}
	

	private  void connectSubscriber(MqttConnectOptions options, IMqttAsyncClient subscriber,List<String> result)
			throws MqttException, MqttSecurityException {
		IMqttActionListener actionListener=new IMqttActionListener() {
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                System.out.println("failed to connect " + exception);
                System.exit(2);
            }

            public void onSuccess(IMqttToken asyncActionToken) {
            	MQTTAsync.subscribe(subscriber,"test/topic",2,(m,msg)->{
            		System.out.println(new String(msg.getPayload()));
            		result.add(new String(msg.getPayload()));
            	}); 
            	System.out.println("Connection successful Sub" + asyncActionToken);
            }
		};
	
		MQTTAsync.connect(options, subscriber, null, actionListener);
	}

}




