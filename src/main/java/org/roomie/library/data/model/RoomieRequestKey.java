package org.roomie.library.data.model;

import java.io.Serializable;
import java.util.Objects;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class RoomieRequestKey implements Serializable{
    private String requestSenderEmail;
    private String requestReceiverEmail;

    public RoomieRequestKey() {}

    public RoomieRequestKey(String requestSenderEmail, String requestReceiverEmail) {
        this.requestSenderEmail = requestSenderEmail;
        this.requestReceiverEmail = requestReceiverEmail;
    }

    @DynamoDBHashKey(attributeName = "requestSenderEmail")
    public String getRequestSenderEmail() {
        return requestSenderEmail;
    }

    public void setRequestSenderEmail(String requestSenderEmail) {
        this.requestSenderEmail = requestSenderEmail;
    }

    @DynamoDBRangeKey(attributeName = "requestReceiverEmail")
    public String getRequestReceiverEmail() {
        return requestReceiverEmail;
    }

    public void setRequestReceiverEmail(String requestReceiverEmail) {
        this.requestReceiverEmail = requestReceiverEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomieRequestKey that = (RoomieRequestKey) o;
        return requestSenderEmail.equals(that.requestSenderEmail) && requestReceiverEmail.equals(that.requestReceiverEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestSenderEmail, requestReceiverEmail);
    }

    @Override
    public String toString() {
        return "RoomieRequestKey{" +
        "requestSenderEmail='" + requestSenderEmail + '\'' +
        ", requestReceiverEmail='" + requestReceiverEmail + '\'' +
        '}';
    }

}
