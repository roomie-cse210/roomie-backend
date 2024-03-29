package org.roomie.library.data.model;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "Roomie_Requests")
public class RoomieRequest {

    @Id
	private RoomieRequestKey RoomieRequestKey;

    private String message;
    private String status;

	public RoomieRequest() {};

	public RoomieRequest(RoomieRequestKey roomieRequestKey, String message, String status) {
		this.RoomieRequestKey = roomieRequestKey;
		this.message = message;
		this.status = status;
	}

	@DynamoDBHashKey(attributeName = "requestSenderEmail")
	public String getRequestSenderEmail() {
		return RoomieRequestKey != null ? RoomieRequestKey.getRequestSenderEmail() : null; 
	}

	public void setRequestSenderEmail(String requestSenderEmail) {
		if (RoomieRequestKey == null) {
			RoomieRequestKey = new RoomieRequestKey();
		}
		RoomieRequestKey.setRequestSenderEmail(requestSenderEmail);
	}

	@DynamoDBRangeKey(attributeName = "requestReceiverEmail")
	public String getRequestReceiverEmail() {
		return RoomieRequestKey != null ? RoomieRequestKey.getRequestReceiverEmail() : null; 
	}

	public void setRequestReceiverEmail(String requestReceiverEmail) {
		if (RoomieRequestKey == null) {
			RoomieRequestKey = new RoomieRequestKey();
		}
		RoomieRequestKey.setRequestReceiverEmail(requestReceiverEmail);
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
                ", RequestSenderEmail='" + RoomieRequestKey.getRequestSenderEmail() + '\'' +
                ", RequestReceiverEmail='" + RoomieRequestKey.getRequestReceiverEmail() + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
				'}';
	}


}
