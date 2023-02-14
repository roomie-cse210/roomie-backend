package com.roomie.roomie.dynamodb.repository;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.roomie.roomie.dynamodb.model.UserInfo;

@EnableScan
public interface UserInfoRepository extends CrudRepository<UserInfo, String> {
    Optional<UserInfo> findById(String id);
}
