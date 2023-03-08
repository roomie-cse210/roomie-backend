package org.roomie.library.data.services;
import org.springframework.stereotype.Service;
import org.roomie.library.data.repositories.UserFiltersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.roomie.library.data.model.RoomieProfile;
import org.roomie.library.data.model.UserFilters;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Service
public class UserFiltersService {
    @Autowired
    UserFiltersRepository userFiltersRepository;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private EmailSenderService emailSenderService;

    private Integer getAgeGroup(Integer age){
        if(age <= 24)
            return 1;
        else if(age <= 35)
            return 2;
        return 3;
    }

    public void getMatchingFilterUserEmail(RoomieProfile roomieProfile) throws Exception {

        Set<String> emails = new HashSet<>();
        Integer ageGroup = getAgeGroup(roomieProfile.getAge());

        // Create a DynamoDB scan request
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                                                .withFilterExpression("(attribute_exists(gender) AND gender = :gender) OR attribute_not_exists(gender)")
                                                .withExpressionAttributeValues(Map.of(":gender", new AttributeValue(roomieProfile.getEmail())))
                                                .withFilterExpression("(attribute_exists(age) AND age = :age) OR attribute_not_exists(age)")
                                                .withExpressionAttributeValues(Map.of(":age", new AttributeValue(String.valueOf(ageGroup))))
                                                .withFilterExpression("((attribute_exists(maxBudget) AND maxBudget >= :approxBudget) OR attribute_not_exists(maxBudget)) AND (attribute_exists(minBudget) AND minBudget <= :approxBudget) OR attribute_not_exists(minBudget))")
                                                .withExpressionAttributeValues(Map.of(":approxBudget", new AttributeValue().withN(String.valueOf(roomieProfile.getApproxBudget()))))
                                                .withFilterExpression("(attribute_exists(nationality) AND nationality = :nationality) OR attribute_not_exists(nationality)")
                                                .withExpressionAttributeValues(Map.of(":nationality", new AttributeValue(roomieProfile.getNationality())))
                                                .withFilterExpression("(attribute_exists(occupation) AND occupation = :occupation) OR attribute_not_exists(occupation)")
                                                .withExpressionAttributeValues(Map.of(":occupation", new AttributeValue(roomieProfile.getOccupation())))
                                                .withFilterExpression("(attribute_exists(smoking) AND smoking = :smoking) OR attribute_not_exists(smoking)")
                                                .withExpressionAttributeValues(Map.of(":smoking", new AttributeValue(roomieProfile.getSmoking())))
                                                .withFilterExpression("(attribute_exists(pets) AND pets = :pets) OR attribute_not_exists(pets)")
                                                .withExpressionAttributeValues(Map.of(":pets", new AttributeValue(roomieProfile.getPets())))
                                                .withFilterExpression("(attribute_exists(food) AND food = :food) OR attribute_not_exists(food)")
                                                .withExpressionAttributeValues(Map.of(":food", new AttributeValue(roomieProfile.getFood())))
                                                .withFilterExpression("(attribute_exists(sleep) AND sleep = :sleep) OR attribute_not_exists(sleep)")
                                                .withExpressionAttributeValues(Map.of(":sleep", new AttributeValue(roomieProfile.getSleep())))
                                                .withFilterExpression("(attribute_exists(riser) AND riser = :riser) OR attribute_not_exists(riser)")
                                                .withExpressionAttributeValues(Map.of(":riser", new AttributeValue(roomieProfile.getRiser())));

        // Execute the scan request
        List<UserFilters> result = dynamoDBMapper.scan(UserFilters.class, scanExpression);

        for (UserFilters rp : result) {
            emails.add(rp.getEmail());
        }

        sendEmailForMatchingRoomie(emails, roomieProfile.getName());
    }

    public void sendEmailForMatchingRoomie(Set<String> emails, String name){
        for(String email: emails){
            emailSenderService.sendEmailForNewMatchingRoomie(email, name);
        }
    }

}
