package com.mirco.mqtt.test;

import java.util.concurrent.TimeUnit;

//import com.hivemq.client.mqtt.MqttClient;
//import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
//import com.hivemq.client.mqtt.datatypes.MqttQos;
//import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient;
//import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient.Mqtt3Publishes;

public class MQTTMainTEST {
//public static void main(String[] args) {
//Mqtt3BlockingClient pubClient = MqttClient.builder()
//                                          .useMqttVersion3()
//                                          .identifier("pub")
//                                          .serverHost("HOST")
//                                          .serverPort(1883)
//                                          .buildBlocking();
// Mqtt3BlockingClient subClient = MqttClient.builder()
//                                           .useMqttVersion3()
//                                           .identifier("sub")
//                                           .serverHost("HOST")
//                                           .serverPort(1883)
//                                           .buildBlocking();
//
//    pubClient.connectWith().keepAlive(10000).send();
//    publish(pubClient, "test/topic", "test");
//    subClient.connectWith().keepAlive(10000).send();
//    subscribe(subClient, "test/topic");
//    while (true) {
//    }
//}
//
//public static void subscribe(Mqtt3BlockingClient client, String topic) {
//    try (final Mqtt3Publishes publishes = 
//              client.publishes(MqttGlobalPublishFilter.ALL)) {
//    	   client
//    	   .subscribeWith()
//    	   .topicFilter(topic)
//    	   .qos(MqttQos.AT_LEAST_ONCE)
//    	   .send();
//    	   
//    	try {
//             publishes.receive(1, TimeUnit.SECONDS)
//            .ifPresent(System.out::println);
//            publishes.receive(100000, TimeUnit.MILLISECONDS)
//            .ifPresent(System.out::println);
//            } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    } catch (Exception e) {
//        // TODO: handle exception
//    }
//
//
//}
//
//public static void publish(Mqtt3BlockingClient client, String topic, 
//String payload) {
//  client
//  .publishWith()
//  .topic(topic)
//  .qos(MqttQos.AT_LEAST_ONCE)
//  .payload(payload.getBytes())
//  .send();
//}
}