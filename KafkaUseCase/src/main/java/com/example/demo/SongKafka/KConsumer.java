package com.example.demo.SongKafka;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.KafkaData.Existence;
import com.example.demo.KafkaData.ResourceNotFoundException;
import com.example.demo.KafkaData.UserRepository;

import io.minio.BucketExistsArgs;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;


@Component
public class KConsumer {

	@Autowired
	private UserRepository userrep;
	MinioClient minioClient = MinioClient.builder().endpoint("http://localhost:9000/").credentials("minioadmin", "minioadmin").build();
	
	@KafkaListener(topics = "MinioOPS", groupId = "GroupA")
	
	
    public void listen( Map<String , String > userMap)throws Exception {
		
		if(!userrep.existsByUsername(userMap.get("username1"))) throw new ResourceNotFoundException(userMap.get("username") + "not found"); 
		if(minioClient.bucketExists(
				BucketExistsArgs.builder()
				.bucket(userMap.get("username1")).build()))
			{
			System.err.println("Bucket name : " + userMap.get("username1")+ " already exists");
			}
		else minioClient.makeBucket(MakeBucketArgs.builder().bucket(userMap.get("username1")).build());
		
		if(minioClient.bucketExists(
				BucketExistsArgs.builder()
				.bucket(userMap.get("username2")).build()))
			{
			System.err.println("Bucket name : " + userMap.get("username2") + " already exists");
			}
		else minioClient.makeBucket(MakeBucketArgs.builder().bucket(userMap.get("username2")).build());
		
		if(new Existence().isObjectExist("pranav.docx", userMap))
		{		minioClient.copyObject(
			    	 CopyObjectArgs.builder()
			        .bucket(userMap.get("username2"))
			        .object("pranav.docx")
			        .source(
			            CopySource.builder()
			                .bucket(userMap.get("username1"))
			                .object("pranav.docx")
			                .build()
			                )
			        .build());
		}
		else throw new ResourceNotFoundException("FILE NOT FOUND");
    }
	
	
}
