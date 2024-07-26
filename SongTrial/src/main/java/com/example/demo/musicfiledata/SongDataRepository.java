package com.example.demo.musicfiledata;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongDataRepository extends MongoRepository<SongData,String > {


//	List<SongData> findBySongnameIgnoreCase(String songname);
//	List<SongData> findBySingernameIgnoreCase(String singername);
//	List<SongData> findByAlbumnameIgnoreCase(String songname);
//	List<SongData> findByGenreIgnoreCase(String singername);
//	List<SongData> findByUsernameIgnoreCase(String username);
	Boolean existsBySongnameIgnoreCase(String songname);
	Boolean existsBySingernameIgnoreCase(String singername);
	

}
