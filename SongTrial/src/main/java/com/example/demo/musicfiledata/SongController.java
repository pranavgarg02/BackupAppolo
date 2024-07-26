package com.example.demo.musicfiledata;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;

@RestController
@RequestMapping("/songs")
public class SongController {

	
	
	MinioClient minioClient = MinioClient.builder().endpoint("http://localhost:9000/").credentials("minioadmin", "minioadmin").build();
	
	@Autowired
	private SongDataRepository songrep;
	
@PostMapping("/uploadsong")
public String songdata_upload(@Validated @RequestParam Map<String,String> sdata , @RequestParam ("musicfile")MultipartFile file)throws Exception {

	SongData song = new SongData(
			sdata.get("genre").toString(),
			sdata.get("albumname").toString(), 
			sdata.get("songname").toString(), 
			sdata.get("singername").toString()
			);
		if(!songrep
				.existsBySongnameIgnoreCase(sdata.get("songname")) || 
				(!songrep.existsBySingernameIgnoreCase(sdata.get("singername")))
				){ 
					try {
						String filename = songrep.save(song).getId();
				if(minioClient.bucketExists(
						BucketExistsArgs.builder()
						.bucket("pranav").build()))
					{System.out.println("Bucket name pranav already exists");}
				else minioClient.makeBucket(MakeBucketArgs.builder().bucket("pranav").build());
			
				System.out.println(filename);
				minioClient.putObject(PutObjectArgs.builder()
						.bucket("pranav")
						.object(filename)
						.stream(
								file.getInputStream(), 
								file.getSize(),
								-1)
						.build());
						}	catch(Exception e) 
							{
							e.printStackTrace();
							throw new IllegalStateException("Something is wrong " , e);
							}
		}	else return sdata.get("songname") + 
				"already exists with singername : " 
				+ sdata.get("singername");
		
		return "DATA SUCCESSFULLY SAVED";
	}
@PutMapping("/Updatesong/{id}")
public ResponseEntity<SongData> Updatestatus(@PathVariable String id , @Validated @RequestParam Map<String,String> songmap , @RequestParam ("musicfile")MultipartFile file)throws Exception {
	SongData sdata = songrep.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
	
	sdata.setSingername(songmap.get("singername"));
	sdata.setAlbumname(songmap.get("albumname"));
	sdata.setGenre(songmap.get("genre"));
	sdata.setsongname(songmap.get("songname"));
	
	String idnew = songrep.save(sdata).getId();
	System.out.println("DATA UPDATED for id : " + id + "DATA UPDATED to id : " + idnew);
	
	if(file!=null) {
	minioClient.putObject(PutObjectArgs.builder()
			.bucket("pranav")
			.object(idnew)
			.stream(file
					.getInputStream(), file.getSize(), -1)
			.build());
	}
	
	return ResponseEntity.ok(sdata);
}


@GetMapping("/getFile/{songname}/{id}")
public ResponseEntity<ByteArrayResource> getFile( String songname, @PathVariable String id) throws MinioException , IOException ,Exception {
	System.out.println(id +" of songname : " + songname );
		InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket("pranav").object(id).build());				
		
		byte arr[] = IOUtils.toByteArray(stream);
		ByteArrayResource resource = new ByteArrayResource(arr);
		return ResponseEntity
				.ok()
				.contentLength(arr.length)
				.header("Content-type", "audio/mp3")
				.header("Content-disposition", "attachment; filename=\"" + songname + "\"")
				.body(resource);
}
	@GetMapping("/getallsongs")
	public List<SongData> displayAll(){
		return songrep.findAll();
	}
	
	@DeleteMapping("deletesong/{id}")
	public void deletesong(@PathVariable String id)throws Exception {
		if(songrep.existsById(id)) {
		minioClient.removeObject(
			    RemoveObjectArgs.builder().bucket("pranav").object(id).build());
		System.out.println(songrep.findById(id).get().getsongname() + "" + "Got deleted");
		songrep.deleteById(id);
		}
		
		
	}
	
	
	}

//
//CREATE DEFINER=`root`@`%` PROCEDURE `insert_personnewdata`()
//BEGIN
//    DECLARE counter INT DEFAULT 100000;
//WHILE counter <= 10000000 DO
//INSERT INTO person (p_id, forcenumber, rank , fathername, emailaddress, gender, age, dob, birthplace)
//VALUES (CONCAT('P00', counter), CONCAT('F11', counter) ,'Michael Johnson', 'F127', 'Private', 'Male', '22', '2002-07-12','Delhi');
// SET counter = counter + 1;
//    END WHILE;
//END
