package org.roomie.library.data.repositories;

//import java.util.*;

import org.roomie.library.data.model.RoomieRequest;
import org.roomie.library.data.model.RoomieRequestKey;
import org.springframework.data.repository.CrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

@EnableScan
public interface RoomieRequestRepository extends CrudRepository<RoomieRequest, RoomieRequestKey>{ 
}
