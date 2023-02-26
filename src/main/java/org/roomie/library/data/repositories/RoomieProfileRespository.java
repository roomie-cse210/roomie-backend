package org.roomie.library.data.repositories;

import java.util.*;

import org.roomie.library.data.model.RoomieProfile;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RoomieProfileRespository extends CrudRepository<RoomieProfile, String> {
    Optional<RoomieProfile> findById(String email);                                     
}
