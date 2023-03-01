package org.roomie.library.data.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class RoomieRequestKey {
    private String requestSenderEmail;
    private String requestReceiverEmail;

    public RoomieRequestKey() {}

    public RoomieRequestKey(String requestSenderEmail, String requestReceiverEmail) {
        this.requestSenderEmail = requestSenderEmail;
        this.requestReceiverEmail = requestReceiverEmail;
    }

    @DynamoDBHashKey
    public String getRequestSenderEmail() {
        return requestSenderEmail;
    }

    public void setRequestSenderEmail(String requestSenderEmail) {
        this.requestSenderEmail = requestSenderEmail;
    }

    @DynamoDBRangeKey
    public String getRequestReceiverEmail() {
        return requestReceiverEmail;
    }

    public void setRequestReceiverEmail(String requestReceiverEmail) {
        this.requestReceiverEmail = requestReceiverEmail;
    }

}
