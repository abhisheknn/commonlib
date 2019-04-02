package com.mirco.mqtt;

import java.util.Optional;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;



public class MQTTAsync {
	
	public static void publish(IMqttAsyncClient publisher,String topic,MqttMessage payload ) {
		try {
			if( publisher.isConnected() && publisher!=null) {
			publisher.publish(topic,payload);
			}
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	public static void subscribe(IMqttAsyncClient subscriber,String topicFilter,int qos ,IMqttMessageListener messageListener) {
		try {
			if( subscriber.isConnected() && subscriber!=null) {
				subscriber.subscribe(topicFilter, qos, messageListener);
				}	
			} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return new MqttAsyncClient client every time you call
	 * @param hostname of mqtt broker
	 * @param clientId of MQTT client
	 *  
	 * **/
	
	public static Optional<IMqttAsyncClient> getClient(String hostName,String clientId) {
		try {
			return Optional.of(new MqttAsyncClient(hostName,clientId));
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	
	public static void connect(MqttConnectOptions options, IMqttAsyncClient publisher, Object userContext, IMqttActionListener actionListener)
			throws MqttException, MqttSecurityException {
		publisher.connect(options,null,actionListener);
	}
}
