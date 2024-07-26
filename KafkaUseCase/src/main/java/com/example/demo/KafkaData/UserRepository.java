package com.example.demo.KafkaData;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<KUserData, String> {
	
	KUserData findByUsername(String username);
	Boolean existsByUsername(String username);

}
