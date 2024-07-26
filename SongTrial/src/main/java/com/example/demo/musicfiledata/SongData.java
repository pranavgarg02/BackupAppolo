package com.example.demo.musicfiledata;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "songdata")
public class SongData {

//	@Id
//	@Indexed(unique=true)
//	private long _id ;
	
	@Id
	private String id;
	
	
	@Field(name="Username")
	private String username;
	
	@Field(name="Genre")
	private String genre;
	
	@Field(name="AlbumName")
	@Indexed(unique = true)
	private String albumname;
	
	@Field(name="SongName")
	private String songname; 
	
	@Field(name="SingerName")
	private String singername;

	
	public SongData( String username, String genre, String albumname, String songname, String singername ) {
		super();
		this.username = username;
		this.genre = genre;
		this.albumname = albumname;
		this.songname = songname;
		this.singername = singername;
	}
//	public SongData(String username, String genre, String albumname, String songname, String singername ) {
//		super();
//		this.username = username;
//		this.genre = genre;
//		this.albumname = albumname;
//		this.songname = songname;
//		this.singername = singername;
//	}
	public SongData( String genre, String albumname, String songname, String singername ) {
		super();
//		this.songID = songname.substring(0, 1) + String.valueOf((Math.random()*876878));
		this.genre = genre;
		this.albumname = albumname;
		this.songname = songname;
		this.singername = singername;
	}
	

	@Override
	public String toString() {
		return "SongData :  username=" + username + ", genre=" + genre + ", albumname=" + albumname
				+ ", songname=" + songname + ", singername=" + singername + "]";
	}


	public SongData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

//
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id =id;
	}

	public String getsongname() {
		return songname;
	}

	public void setsongname(String songname) {
		this.songname = songname;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public String getAlbumname() {
		return albumname;
	}


	public void setAlbumname(String albumname) {
		this.albumname = albumname;
	}


	public String getSingername() {
		return singername;
	}


	public void setSingername(String singername) {
		this.singername = singername;
	}
	
//	public void setFile(MultipartFile file) {
//		this.file = null;
//	}
	
//	public MultipartFile getFile() {
//		return file;
//	}

	


}
