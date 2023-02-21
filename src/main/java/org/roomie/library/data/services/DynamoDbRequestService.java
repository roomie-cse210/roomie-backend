package org.roomie.library.data.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.roomie.library.data.repositories.RoomieProfileRespository;
import org.roomie.library.data.model.RoomieProfileFilterRequest;
import java.util.*;
import org.roomie.library.data.model.RoomieProfile;
import com.fasterxml.jackson.databind.ObjectMapper;  

@Service
public class DynamoDbRequestService {
    @Autowired
    RoomieProfileRespository roomieProfileRespository;

    public List<String> getFilteredRecords(RoomieProfileFilterRequest roomieProfileFilterRequest) throws Exception {
        List<String> jsonStr = new ArrayList<String>();
        var val = roomieProfileRespository.findByGenderAndAgeAndNationalityAndOccupationAndMinBudgetAndMaxBudgetAndSmokingAndPetsAndFoodAndRiserAndSleepAndIsPrivate(
            roomieProfileFilterRequest.getGender(), 
            roomieProfileFilterRequest.getAge(),
            roomieProfileFilterRequest.getNationality(),
            roomieProfileFilterRequest.getOccupation(),
            roomieProfileFilterRequest.getMinBudget(),
            roomieProfileFilterRequest.getMaxBudget(),
            roomieProfileFilterRequest.getSmoking(),
            roomieProfileFilterRequest.getPets(),
            roomieProfileFilterRequest.getFood(),
            roomieProfileFilterRequest.getRiser(),
            roomieProfileFilterRequest.getSleep(),
            "N"
        );

        if (val.isPresent()) 
			jsonStr = convertJavaObjectToJSON(val.get());

        return jsonStr;
        
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
