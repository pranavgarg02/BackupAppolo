package com.example.demo.SongKafka;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Kproducer {
	
	@Autowired
    private KafkaTemplate<String, Map<String , String> > kafkaTemplate;
	
    public void sendMessage(String topic , Map<String , String > userMap) {
    	
        kafkaTemplate.send(topic, userMap);
        
    }

}
