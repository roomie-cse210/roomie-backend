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

    private boolean allFieldsNull(RoomieProfileFilterRequest roomieProfileFilterRequest){
        if(roomieProfileFilterRequest.getGender() == null &&  
        roomieProfileFilterRequest.getMinAge() == null &&  
        roomieProfileFilterRequest.getMaxAge() == null &&  
        roomieProfileFilterRequest.getNationality() == null &&  
        roomieProfileFilterRequest.getOccupation() == null &&  
        roomieProfileFilterRequest.getMinBudget() == null &&  
        roomieProfileFilterRequest.getMaxBudget() == null &&  
        roomieProfileFilterRequest.getSmoking() == null &&  
        roomieProfileFilterRequest.getPets() == null &&  
        roomieProfileFilterRequest.getFood() == null &&  
        roomieProfileFilterRequest.getRiser() == null &&  
        roomieProfileFilterRequest.getSleep() == null )
            return true;
        return false;
    }

    private boolean allFieldsPresent(RoomieProfileFilterRequest roomieProfileFilterRequest){
        if(roomieProfileFilterRequest.getGender() != null &&  
        roomieProfileFilterRequest.getMinAge() != null &&  
        roomieProfileFilterRequest.getMaxAge() != null &&  
        roomieProfileFilterRequest.getNationality() != null &&  
        roomieProfileFilterRequest.getOccupation() != null &&  
        roomieProfileFilterRequest.getMinBudget() != null &&  
        roomieProfileFilterRequest.getMaxBudget() != null &&  
        roomieProfileFilterRequest.getSmoking() != null &&  
        roomieProfileFilterRequest.getPets() != null &&  
        roomieProfileFilterRequest.getFood() != null &&  
        roomieProfileFilterRequest.getRiser() != null &&  
        roomieProfileFilterRequest.getSleep() != null )
            return true;
        return false;
    }


    public List<String> getFilteredRecords(RoomieProfileFilterRequest roomieProfileFilterRequest) throws Exception {
        List<String> jsonStr = new ArrayList<String>();

        if(allFieldsNull(roomieProfileFilterRequest)){
            return getAllUserProfiles();
        }

        List<RoomieProfile> val = Optional.of(new ArrayList<RoomieProfile>()).get();
        if(allFieldsPresent(roomieProfileFilterRequest)){
            val = roomieProfileRespository.findByGenderAndAgeBetweenAndNationalityAndOccupationAndApproxBudgetBetweenAndSmokingAndPetsAndFoodAndRiserAndSleepAndIsPrivate(
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
            ).get()   ;
        }
        else{
            
            if(roomieProfileFilterRequest.getGender() != null){
                var temp = roomieProfileRespository.findByGender(roomieProfileFilterRequest.getGender()).get();
                if(val.isEmpty()){
                    val = temp;
                }
                else{
                    val.retainAll(temp);
                }
            }

            if(roomieProfileFilterRequest.getMaxAge() != null){
                var temp = roomieProfileRespository.findByAgeBetween(roomieProfileFilterRequest.getMinAge(),roomieProfileFilterRequest.getMaxAge()).get();
                if(val.isEmpty()){
                    val = temp;
                }
                else{
                    val.retainAll(temp);
                }
                
            }

            if(roomieProfileFilterRequest.getNationality() != null){
                var temp = roomieProfileRespository.findByNationality(roomieProfileFilterRequest.getNationality()).get();
                if(val.isEmpty()){
                    val = temp;
                }
                else{
                    val.retainAll(temp);
                }
            }

            if(roomieProfileFilterRequest.getOccupation() != null){
                var temp = roomieProfileRespository.findByOccupation(roomieProfileFilterRequest.getOccupation()).get();
                if(val.isEmpty()){
                    val = temp;
                }
                else{
                    val.retainAll(temp);
                }
            }

            if(roomieProfileFilterRequest.getMaxBudget() != null){
                var temp = roomieProfileRespository.findByApproxBudgetBetween(roomieProfileFilterRequest.getMinBudget(), roomieProfileFilterRequest.getMaxBudget()).get();
                if(val.isEmpty()){
                    val = temp;
                }
                else{
                    val.retainAll(temp);
                }
            }

            if(roomieProfileFilterRequest.getSmoking() != null){
                var temp = roomieProfileRespository.findBySmoking(roomieProfileFilterRequest.getSmoking()).get();
                if(val.isEmpty()){
                    val = temp;
                }
                else{
                    val.retainAll(temp);
                }
            }

            if(roomieProfileFilterRequest.getPets() != null){
                var temp = roomieProfileRespository.findByPets(roomieProfileFilterRequest.getPets()).get();
                if(val.isEmpty()){
                    val = temp;
                }
            }

            if(roomieProfileFilterRequest.getFood() != null){
                var temp = roomieProfileRespository.findByFood(roomieProfileFilterRequest.getFood()).get();
                if(val.isEmpty()){
                    val = temp;
                }
                else{
                    val.retainAll(temp);
                }
            }

            if(roomieProfileFilterRequest.getRiser() != null){
                var temp = roomieProfileRespository.findByRiser(roomieProfileFilterRequest.getRiser()).get();
                if(val.isEmpty()){
                    val = temp;
                }
                else{
                    val.retainAll(temp);
                }
            }

            if(roomieProfileFilterRequest.getSleep() != null){
                var temp = roomieProfileRespository.findBySleep(roomieProfileFilterRequest.getSleep()).get();
                if(val.isEmpty()){
                    val = temp;
                }
                else{
                    val.retainAll(temp);
                }
            }
        }
        jsonStr = convertJavaObjectToJSON(val);
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
