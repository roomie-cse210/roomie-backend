package org.roomie.library.web;
import org.roomie.library.data.model.UserInfo;
import java.util.Random;
import org.roomie.library.data.repositories.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
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
				return ResponseEntity.status(200).body("login successful");
			} else{
				logger.info("Login Failed Due to Incorrect Password.");
				return ResponseEntity.status(401).body("incorrect password");
			}
		} else {
			logger.info("No user found with username {}", userInfo.getEmail());
			return ResponseEntity.status(419).body("user not found");
		}
	}

	@PostMapping("/createUser")
	public ResponseEntity<String> createUser(@RequestBody UserInfo userInfo) {
		logger.info("Checking whether email is already registered");
		var val = userInfoRepository.findById(userInfo.getEmail());
		if (val.isPresent()) {
			logger.info("User {} is already registered", userInfo.getEmail());
			return ResponseEntity.status(420).body("user already registered");
		} else {
			var uinfo = userInfoRepository.save(userInfo);
			logger.info("Created user {} successfully", uinfo.getEmail());
			return ResponseEntity.status(200).body("user created");
		}
	}

	@PostMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody UserInfo userInfo) {
		logger.info("Checking whether email is registered");
		var val = userInfoRepository.findById(userInfo.getEmail());
		if (!val.isPresent()) {
			logger.info("User {} is not registered", userInfo.getEmail());
			return ResponseEntity.status(419).body("user not registered");
		} else {
			var uinfo = userInfoRepository.save(userInfo);
			logger.info("Updated user {} password to {}", uinfo.getEmail(), uinfo.getPassword());
			return ResponseEntity.status(200).body("user updated");
		}
	}
	@PostMapping("/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestBody UserInfo userInfo) {
		logger.info("Checking whether email is registered");
		var val = userInfoRepository.findById(userInfo.getEmail());
		if (!val.isPresent()) {
			logger.info("User {} is not registered", userInfo.getEmail());
			return ResponseEntity.status(419).body("user not registered");
		} else {
			// generate random password
			String randomPassword = generateRandomString();
			logger.info("Generated random password {}", randomPassword);
			// send email to user sharing random password

			// update the DB to use random password for user
			userInfo.setPassword(randomPassword);
			var uinfo = userInfoRepository.save(userInfo);
			logger.info("Updated user {} password to {}", uinfo.getEmail(), randomPassword);
			return ResponseEntity.status(200).body("user updated");
		}
	}

	@GetMapping("/")
	public ResponseEntity<String> getAllUsers() {
		StringBuilder sb = new StringBuilder();
    	userInfoRepository.findAll().forEach(sb::append);
		return ResponseEntity.ok(sb.toString());
	}

	static String generateRandomString(){
		// create a string of all characters
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// create random string builder
		StringBuilder sb = new StringBuilder();
	
		// create an object of Random class
		Random random = new Random();
	
		// specify length of random string
		int length = 7;
	
		for(int i = 0; i < length; i++) {
	
		  // generate random index number
		  int index = random.nextInt(alphabet.length());
	
		  // get character specified by index
		  // from the string
		  char randomChar = alphabet.charAt(index);
	
		  // append the character to string builder
		  sb.append(randomChar);
		}
	
		String randomString = sb.toString();
		return randomString;
	}
}
