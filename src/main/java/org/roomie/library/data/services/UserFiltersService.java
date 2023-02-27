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


    public void getMatchingFilterUserEmail(RoomieProfile roomieProfile) throws Exception {

        Set<String> emails = new HashSet<>();

        // Create a DynamoDB scan request
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();

        // Set filter expressions based on input parameters
        Map<String, Condition> scanExpression = new HashMap<>();

        scanExpression.put("email", new Condition().withComparisonOperator(ComparisonOperator.NE).withAttributeValueList(new AttributeValue(roomieProfile.getEmail())));
        scanExpression.put("gender", new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue(roomieProfile.getGender())));


        // Set filter expression in scan request
        if (!scanExpression.isEmpty()) {
            scanExp.setScanFilter(scanExpression);
        }

        // Execute the scan request
        List<UserFilters> result = dynamoDBMapper.scan(UserFilters.class, scanExp);

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
