package org.roomie.library.data.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.roomie.library.data.repositories.RoomieProfileRespository;
import org.roomie.library.data.model.RoomieProfileFilterRequest;
import java.util.*;
import org.roomie.library.data.model.RoomieProfile;
import org.roomie.library.data.model.RoomieRequest;
import org.roomie.library.data.model.RoomieRequestKey;
import com.fasterxml.jackson.databind.ObjectMapper;  
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;


@Service
public class DynamoDbRequestService {
    @Autowired
    RoomieProfileRespository roomieProfileRespository;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;


    public String getUserProfile(String email) throws Exception {
        String jsonStr = new String();
        var val = roomieProfileRespository.findById(email);
        if (val.isPresent()){
            ObjectMapper mapper = new ObjectMapper();  
            jsonStr = mapper.writeValueAsString(val.get());
        }
        return jsonStr;
    }

    public List<String> getAllUserProfiles() throws Exception {
        List<RoomieProfile> profiles = new ArrayList<RoomieProfile>();
        roomieProfileRespository.findAll().forEach(profiles::add);

        List<String> jsonStrList = new ArrayList<String>();
        for(int i =0; i<profiles.size(); i++){
			ObjectMapper mapper = new ObjectMapper();  
			jsonStrList.add(mapper.writeValueAsString(profiles.get(i)));
		}
        return jsonStrList;
    }

    public RoomieRequest getConnectionRequest(RoomieRequestKey roomieRequestKey){
        List<RoomieRequest> result = new ArrayList<>();

        // Create a DynamoDB scan request
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();

        // Set filter expressions based on input parameters
        Map<String, Condition> scanExpression = new HashMap<>();

        scanExpression.put("requestSenderEmail", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieRequestKey.getRequestSenderEmail() )));
        scanExpression.put("requestReceiverEmail", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieRequestKey.getRequestReceiverEmail() )));

        // Set filter expression in scan request
        if (!scanExpression.isEmpty()) {
            scanExp.setScanFilter(scanExpression);
        }
        // Execute the scan request
        System.out.println("Dynamo Service starts");
        result = dynamoDBMapper.scan(RoomieRequest.class, scanExp);
        System.out.println(result);
        return result.get(0);
    }

    public Map<String, Object> getConnections(String email){
        Map<String, Object> result = new HashMap<>();
        List<RoomieRequest> userReceivedRequests = new ArrayList<>();
        List<RoomieRequest> userSentRequests = new ArrayList<>();
    
        //get connections where requestSenderEmail is equal to email
        DynamoDBScanExpression scanExp1 = new DynamoDBScanExpression();
        // Set filter expressions based on input parameters
        Map<String, Condition> scanExpression1 = new HashMap<>();
        scanExpression1.put("requestSenderEmail", new Condition()
                    .withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(email)));
        // Set filter expression in scan request
        if (!scanExpression1.isEmpty()) {
            scanExp1.setScanFilter(scanExpression1);
        }
        userSentRequests = dynamoDBMapper.scan(RoomieRequest.class, scanExp1);

        //get connections where requestRequesterEmail is equal to email
        DynamoDBScanExpression scanExp2 = new DynamoDBScanExpression();
        // Set filter expressions based on input parameters
        Map<String, Condition> scanExpression2 = new HashMap<>();
        scanExpression2.put("requestReceiverEmail", new Condition()
                    .withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(email)));
        // Set filter expression in scan request
        if (!scanExpression2.isEmpty()) {
            scanExp2.setScanFilter(scanExpression2);
        }
        userReceivedRequests = dynamoDBMapper.scan(RoomieRequest.class, scanExp2);

        // join all connections
        List<RoomieRequest> allconnections= new ArrayList<>();
        Stream.of(userReceivedRequests, userSentRequests).forEach(allconnections::addAll);

        // separate all connections into accepted connections/sent request/received requests
        // AND collect each user email
        List<RoomieRequest> connections = new ArrayList<>(); //(S=email or R= email) and status=A
        List<RoomieRequest> sentRequests = new ArrayList<>(); // S=email and (status=R or status=P)
        List<RoomieRequest> receivedRequests = new ArrayList<>(); // R=email and status=P)
        List<String> userEmails = new ArrayList<>();
        for (RoomieRequest conn : allconnections){
            String status = conn.getStatus();
            String senderEmail = conn.getRequestSenderEmail();
            String receiverEmail = conn.getRequestReceiverEmail();
            // separate
            if (status.equals("A")){
                connections.add(conn);
            }else if (senderEmail.equals(email)){
                sentRequests.add(conn);
            }else{
                receivedRequests.add(conn);
            }
            // add to userEmails
            if (!senderEmail.equals(email)){
                userEmails.add(senderEmail);
            }
            if (!receiverEmail.equals(email)){
                userEmails.add(receiverEmail);
            }
        }

        // get user profile
        Map<String, RoomieProfile> userProfiles = new HashMap<>();
        for (String em : userEmails){
            var profile = roomieProfileRespository.findById(em);
            try{
                userProfiles.put(em, profile.get());
            }catch (Exception except) {
                System.out.println(except);
            }
        }
        
        // result
        result.put("userProfiles", userProfiles);
        result.put("allConnections", connections);
        result.put("receivedRequests", receivedRequests);
        result.put("sentRequests", sentRequests);

        return result;
    }

    private boolean allFieldsNull(RoomieProfileFilterRequest roomieProfileFilterRequest){
        if(roomieProfileFilterRequest.getGender() == null &&  
        roomieProfileFilterRequest.getMinAge() == 0 &&  
        roomieProfileFilterRequest.getMaxAge() == 1000 &&  
        roomieProfileFilterRequest.getNationality() == null &&  
        roomieProfileFilterRequest.getOccupation() == null &&  
        roomieProfileFilterRequest.getMinBudget() == 0 &&  
        roomieProfileFilterRequest.getMaxBudget() == null &&  
        roomieProfileFilterRequest.getSmoking() == null &&  
        roomieProfileFilterRequest.getPets() == null &&  
        roomieProfileFilterRequest.getFood() == null &&  
        roomieProfileFilterRequest.getRiser() == null &&  
        roomieProfileFilterRequest.getSleep() == null )
            return true;
        return false;
    }
    
    public List<String> getFilteredRecords(RoomieProfileFilterRequest roomieProfileFilterRequest) throws Exception {

        List<RoomieProfile> result = new ArrayList<>();

        if (allFieldsNull(roomieProfileFilterRequest)) {
            return convertJavaObjectToJSON(result); // Return empty list if all input parameters are null
        }

        // Create a DynamoDB scan request
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();

        // Set filter expressions based on input parameters
        Map<String, Condition> scanExpression = new HashMap<>();

        // Set filter expressions based on input parameters
        scanExpression.put("email", new Condition().withComparisonOperator(ComparisonOperator.NE).withAttributeValueList(new AttributeValue(roomieProfileFilterRequest.getEmail())));
        if (roomieProfileFilterRequest.getGender() != null) {
            scanExpression.put("gender", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieProfileFilterRequest.getGender())));
        }
        if (roomieProfileFilterRequest.getNationality() != null) {
            scanExpression.put("nationality", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieProfileFilterRequest.getNationality() )));
        }
        if (roomieProfileFilterRequest.getMinAge() != -1 && roomieProfileFilterRequest.getMaxAge() != 1000) {
            scanExpression.put("age", new Condition().withComparisonOperator(ComparisonOperator.BETWEEN).withAttributeValueList(new AttributeValue().withN(Integer.toString(roomieProfileFilterRequest.getMinAge())), new AttributeValue().withN(Integer.toString(roomieProfileFilterRequest.getMaxAge()))));
        }
        if (roomieProfileFilterRequest.getOccupation() != null) {
            scanExpression.put("occupation", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieProfileFilterRequest.getOccupation())));
        }
        if (roomieProfileFilterRequest.getMinBudget() != null && roomieProfileFilterRequest.getMaxBudget() != null) {
            scanExpression.put("approxBudget", new Condition().withComparisonOperator(ComparisonOperator.BETWEEN).withAttributeValueList(new AttributeValue().withN(Integer.toString(roomieProfileFilterRequest.getMinBudget())), new AttributeValue().withN(Integer.toString(roomieProfileFilterRequest.getMaxBudget()))));
        } else if (roomieProfileFilterRequest.getMinBudget() != null) {
            scanExpression.put("approxBudget", new Condition().withComparisonOperator(ComparisonOperator.GE).withAttributeValueList(new AttributeValue().withN(Integer.toString(roomieProfileFilterRequest.getMinBudget()))));
        } else if (roomieProfileFilterRequest.getMaxBudget() != null) {
            scanExpression.put("approxBudget", new Condition().withComparisonOperator(ComparisonOperator.LE).withAttributeValueList(new AttributeValue().withN(Integer.toString(roomieProfileFilterRequest.getMaxBudget()))));
        }
        if (roomieProfileFilterRequest.getSmoking() != null) {
            scanExpression.put("smoking", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieProfileFilterRequest.getSmoking())));
        }
        if (roomieProfileFilterRequest.getPets() != null) {
            scanExpression.put("pets", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieProfileFilterRequest.getPets())));
        }
        if (roomieProfileFilterRequest.getFood() != null) {
            scanExpression.put("food", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieProfileFilterRequest.getFood())));
        }
        if (roomieProfileFilterRequest.getRiser() != null) {
            scanExpression.put("riser", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieProfileFilterRequest.getRiser())));
        }
        if (roomieProfileFilterRequest.getSleep() != null) {
            scanExpression.put("sleep", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieProfileFilterRequest.getSleep())));
        }

        // Set filter expression in scan request
        if (!scanExpression.isEmpty()) {
            scanExp.setScanFilter(scanExpression);
        }

        // Execute the scan request
        result = dynamoDBMapper.scan(RoomieProfile.class, scanExp);

        return convertJavaObjectToJSON(result);
    }
    
    private List<String> convertJavaObjectToJSON(List<RoomieProfile> obj) throws Exception {
		List<String> jsonStrList = new ArrayList<String>();
		for(int i =0; i<obj.size(); i++){
			ObjectMapper mapper = new ObjectMapper();  
			jsonStrList.add(mapper.writeValueAsString(obj.get(i)));
		}
		return  jsonStrList;
	}
}
