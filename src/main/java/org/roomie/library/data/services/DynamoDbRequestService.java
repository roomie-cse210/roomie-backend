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

    public List<String> getFilteredRecords(RoomieProfileFilterRequest roomieProfileFilterRequest) throws Exception {
        List<String> jsonStr = new ArrayList<String>();
        var val = roomieProfileRespository.findByGenderAndAgeBetweenAndNationalityAndOccupationAndMinBudgetAndMaxBudgetAndSmokingAndPetsAndFoodAndRiserAndSleepAndIsPrivate(
            roomieProfileFilterRequest.getGender(), 
            roomieProfileFilterRequest.getMinAge(),
            roomieProfileFilterRequest.getMaxAge(),
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
