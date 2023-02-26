package org.roomie.library.data.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.roomie.library.data.repositories.RoomieProfileRespository;
import org.roomie.library.data.model.RoomieProfileFilterRequest;
import java.util.*;
import org.roomie.library.data.model.RoomieProfile;
import com.fasterxml.jackson.databind.ObjectMapper;  
import java.util.ArrayList;
import java.util.List;
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
