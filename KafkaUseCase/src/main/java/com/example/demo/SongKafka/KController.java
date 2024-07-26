package com.example.demo.SongKafka;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.KafkaData.KUserData;
import com.example.demo.KafkaData.UserRepository;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
@RestController
@RequestMapping("/Happy")
public class KController {

	@Autowired
	private Kproducer kp;
	@Autowired
	private UserRepository userrep;
	
	
	MinioClient minioClient = MinioClient.builder().endpoint("http://localhost:9000/").credentials("minioadmin", "minioadmin").build();
	
	@PostMapping("/send")
    public String Message(@RequestParam Map<String , String> userMap) {
       kp.sendMessage("MinioOPS", userMap);
        return "file copied from : " + userMap.get("username1") + " to " + userMap.get("username2");
    }
	
	@PostMapping("/upload")
	public void upload(@Validated @RequestParam("username") String username , @PathVariable MultipartFile file) throws Exception
	{
		if(!userrep.existsByUsername(username)) userrep.save( new KUserData(username));
		else System.err.println("user with { " + username + " } already exists");
		
		if(minioClient.bucketExists(
				BucketExistsArgs.builder()
				.bucket(username).build()))
			{
			System.err.println("Bucket name" + username + " already exists");
			}
		else minioClient.makeBucket(MakeBucketArgs.builder().bucket(username).build());
		if(file!=null)
		minioClient.putObject(PutObjectArgs.builder()
				.bucket(username)
				.object(file.getOriginalFilename())
				.stream(
						file.getInputStream(), 
						file.getSize(),
						-1)
				.build());
	}
	
	
}
