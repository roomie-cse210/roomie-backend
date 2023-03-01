package org.roomie.library.data.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "RoomieRequests")
public class RoomieRequest {
    private String requestSenderEmail;
    private String requestReceiverEmail;
    private String message;
    private String status;

	public RoomieRequest(String requestSenderEmail, String requestReceiverEmail, String message, String status) {
		this.requestSenderEmail = requestSenderEmail;
		this.requestReceiverEmail = requestReceiverEmail;
		this.message = message;
		this.status = status;
	}

	@DynamoDBHashKey
	public String getRequestSenderEmail() {
		return requestSenderEmail;
	}

    @DynamoDBAttribute(attributeName = "requestReceiverEmail")
	public String getRequestReceiverEmail() {
		return requestReceiverEmail;
	}

	public void setrequestReceiverEmail(String requestReceiverEmail) {
		this.requestReceiverEmail = requestReceiverEmail;
	}

	public void setRequestSenderEmail(String requestSenderEmail) {
		this.requestSenderEmail = requestSenderEmail;
	}

    @DynamoDBAttribute(attributeName = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    @DynamoDBAttribute(attributeName = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status =status;
	}

	@Override
	public String toString() {
		return "RoomieRequest{" +
                ", RequestSenderEmail='" + requestSenderEmail + '\'' +
                ", RequestReceiverEmail='" + requestReceiverEmail + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
				'}';
	}


}
