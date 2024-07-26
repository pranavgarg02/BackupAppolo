package com.example.demo.KafkaData;

import java.util.Map;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;

public class Existence {
	
	MinioClient minioClient = MinioClient.builder()
			.endpoint("http://localhost:9000/")
			.credentials("minioadmin", "minioadmin")
			.build();
	
	public boolean isObjectExist(String filename , Map<String , String> userMap) {
		
        try {
            minioClient.getObject(GetObjectArgs.builder()
                    .bucket(userMap.get("username1"))
                    .object(filename).build());
            return true;
        } catch (ErrorResponseException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
	}
}
