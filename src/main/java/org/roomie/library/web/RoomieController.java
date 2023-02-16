package org.roomie.library.web;

import org.roomie.library.data.model.UserInfo;
import org.roomie.library.data.repositories.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoomieController {

	private static final Logger logger = LoggerFactory.getLogger( RoomieController.class);

	@Autowired
	UserInfoRepository userInfoRepository;

	@PostMapping("/verifyUser")
	public ResponseEntity<String> verifyUser(@RequestBody UserInfo userInfo) {
		logger.info("Checking login credentials");
		var val = userInfoRepository.findById(userInfo.getEmail());
		if (val.isPresent()) {
			var uinfo = val.get();
			if(uinfo.getPassword().equals(userInfo.getPassword())){
				logger.info("Login Successful");
				return ResponseEntity.ok("login successful");
			} else{
				logger.info("Login Failed Due to Incorrect Password.");
				return ResponseEntity.ok("incorrect password");
			}
		} else {
			logger.info("No user found with username {}", userInfo.getEmail());
			return ResponseEntity.ok("user not found");
		}
	}

	@PostMapping("/createUser")
	public ResponseEntity<String> createUser(@RequestBody UserInfo userInfo) {
		logger.info("Checking whether user is already registered");
		var val = userInfoRepository.findById(userInfo.getEmail());
		if (val.isPresent()) {
			logger.info("User {} is already registered", userInfo.getEmail());
			return ResponseEntity.ok("user already registered");
		} else {
			var uinfo = userInfoRepository.save(userInfo);
			logger.info("Created user {} successfully", uinfo.getEmail());
			return ResponseEntity.ok("user created");
		}
	}

	@PostMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody UserInfo userInfo) {
		logger.info("Checking whether user is registered");
		var val = userInfoRepository.findById(userInfo.getEmail());
		if (!val.isPresent()) {
			logger.info("User {} is not registered", userInfo.getEmail());
			return ResponseEntity.ok("user not registered");
		} else {
			var uinfo = userInfoRepository.save(userInfo);
			logger.info("Updated user {} successfully", uinfo.getEmail());
			return ResponseEntity.ok("user updated");
		}
	}

	@GetMapping("/")
	public ResponseEntity<String> getAllUsers() {
		StringBuilder sb = new StringBuilder();
    	userInfoRepository.findAll().forEach(sb::append);
		return ResponseEntity.ok(sb.toString());
	}
}
