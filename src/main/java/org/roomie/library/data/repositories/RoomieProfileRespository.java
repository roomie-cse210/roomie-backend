package org.roomie.library.data.repositories;

import java.util.*;

import org.roomie.library.data.model.RoomieProfile;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RoomieProfileRespository extends CrudRepository<RoomieProfile, String> {
    Optional<RoomieProfile> findById(String email);

    Optional<List<RoomieProfile>> findByGenderAndAgeBetweenAndNationalityAndOccupationAndMinBudgetAndMaxBudgetAndSmokingAndPetsAndFoodAndRiserAndSleepAndIsPrivate(
                                                String gender,Integer minAge,Integer maxAge, String nationality, String occupation,Integer minBudget,
                                                Integer maxBudget,String smoking,String pets, String food,String riser,String sleep, String isPrivate );
                                                
}
