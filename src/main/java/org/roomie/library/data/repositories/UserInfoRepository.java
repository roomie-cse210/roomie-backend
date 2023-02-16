package org.roomie.library.data.repositories;

import java.util.Optional;

import org.roomie.library.data.model.UserInfo;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UserInfoRepository  extends CrudRepository<UserInfo, String> {
	Optional<UserInfo> findById(String email);
}