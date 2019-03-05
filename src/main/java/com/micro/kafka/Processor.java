package com.micro.kafka;

import org.apache.kafka.streams.StreamsBuilder;

//import org.apache.kafka.streams.StreamsBuilder;

public interface Processor {
	
public StreamsBuilder execute();

}
