package com.micro.kafka;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

public class StreamProcessor{

	private Properties props= new Properties();
	private Processor processor;
	private static Builder builder;
	
	private void setProperties(Properties properties) {
		this.props=properties;
		System.out.println(props);
	}
	
	private void setProcessor(Processor processor) {
		this.processor=processor;
	}
	public static Builder build() {
		builder=new Builder();
		return builder;
	} 
	
	public void start() {
		
		StreamsBuilder builder = (StreamsBuilder) processor.execute();
        final Topology topology = builder.build();
        final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
	}
	
	
	public static class Builder {
		private StreamProcessor  streamProcessor=new StreamProcessor();
		
		public Builder withProperties(Properties props) {
			if(null==props.get(StreamsConfig.APPLICATION_ID_CONFIG)) throw new ConfigException(StreamsConfig.APPLICATION_ID_CONFIG +" not present");
			if(null==props.get(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG)) props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("KAFKABROKERS"));
			if(null==props.get(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG)) props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
			if(null==props.get(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG)) props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	        streamProcessor.setProperties(props);
			return builder;
		}
		
		public StreamProcessor withProcessor(Processor processor) {
				streamProcessor.setProcessor(processor);
				return streamProcessor;
		}
	};
}
