package org.roomie.library.data.repositories;

import java.util.*;

import org.roomie.library.data.model.RoomieProfile;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RoomieProfileRespository extends CrudRepository<RoomieProfile, String> {
    Optional<RoomieProfile> findById(String email);

    Optional<List<RoomieProfile>> findByGender(String gender);

    Optional<List<RoomieProfile>> findByNationality(String nationality);

    Optional<List<RoomieProfile>> findBySmoking(String smoking);

    Optional<List<RoomieProfile>> findByFood(String food);

    Optional<List<RoomieProfile>> findByPets(String pets);

    Optional<List<RoomieProfile>> findByOccupation(String occupation);

    Optional<List<RoomieProfile>> findByRiser(String riser);

    Optional<List<RoomieProfile>> findBySleep(String sleep);

    Optional<List<RoomieProfile>> findByAgeBetween(Integer minAge,Integer maxAge);

    Optional<List<RoomieProfile>> findByApproxBudgetBetween(Integer minBudget,Integer maxBudget);

    Optional<List<RoomieProfile>> findByGenderAndAgeBetweenAndNationalityAndOccupationAndApproxBudgetBetweenAndSmokingAndPetsAndFoodAndRiserAndSleepAndIsPrivate(
                                                String gender,Integer minAge,Integer maxAge, String nationality, String occupation,Integer minBudget,
                                                Integer maxBudget,String smoking,String pets, String food,String riser,String sleep, String isPrivate );
                                                
}
