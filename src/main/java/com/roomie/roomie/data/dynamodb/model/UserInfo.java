package com.roomie.roomie.data.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "UserInfo")
public class UserInfo {
    private String id;
    private String msrp;
    private String cost;

    public UserInfo() {
    }

    public UserInfo(String cost, String msrp) {
        this.msrp = msrp;
        this.cost = cost;
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    @DynamoDBAttribute
    public String getMsrp() {
        return msrp;
    }

    @DynamoDBAttribute
    public String getCost() {
        return cost;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMsrp(String msrp) {
        this.msrp = msrp;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
