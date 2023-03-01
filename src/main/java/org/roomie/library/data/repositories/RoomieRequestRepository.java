package org.roomie.library.data.repositories;

import java.util.*;

import org.roomie.library.data.model.RoomieRequest;
import org.springframework.data.repository.CrudRepository;

public interface RoomieRequestRepository extends CrudRepository<RoomieRequest, String>{
    Optional<RoomieRequest> findById(String requestSenderEmail);    
}
