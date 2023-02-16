package org.roomie.library.data.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
// import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName = "Users")
public class UserInfo {
	private String password;
	private String email;

	public UserInfo() {
	}

	public UserInfo(String email) {
		this.email = email;
	}
	
	@DynamoDBHashKey
	public String getEmail() {
		return email;
	}

	@DynamoDBAttribute
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
