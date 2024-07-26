package com.example.demo.KafkaData;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.annotation.Nonnull;

@Document(collection = "KafkaCred")
public class KUserData {

		@Nonnull
		@Indexed(unique = true)
		private String username;
		
		@Id
		private String _id;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String get_id() {
			return _id;
		}

		public void set_id(String _id) {
			this._id = _id;
		}

		public KUserData(String username, String _id) {
			super();
			this.username = username;
			this._id = _id;
		}
		
		public KUserData(String username) {
			super();
			this.username = username;
		}

		public KUserData() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "KUserData [username=" + username + ", _id=" + _id + "]";
		}
}
