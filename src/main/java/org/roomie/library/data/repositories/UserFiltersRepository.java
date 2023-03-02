package org.roomie.library.data.repositories;

// import java.util.*;

import org.roomie.library.data.model.UserFilters;
import org.roomie.library.data.model.UserFiltersKey;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UserFiltersRepository extends CrudRepository<UserFilters, UserFiltersKey> {
}
