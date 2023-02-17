package org.roomie.library.web;
import org.roomie.library.data.model.UserInfo;
import org.roomie.library.data.repositories.UserInfoRepository;
import org.roomie.library.data.services.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RoomieController {

	private static final Logger logger = LoggerFactory.getLogger( RoomieController.class);

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@PostMapping("/verifyUser")
	public ResponseEntity<String> verifyUser(@RequestBody UserInfo userInfo) {
		try{
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
		} catch(Exception e){
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/createUser")
	public ResponseEntity<String> createUser(@RequestBody UserInfo userInfo) {
		try{
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
		} catch(Exception e){
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody UserInfo userInfo) {
		try{
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
		} catch(Exception e){
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestHeader(value="email") String email) {
		logger.info("Checking whether email is registered");
		try{
			var val = userInfoRepository.findById(email);

			if (!val.isPresent()) {
				logger.info("User {} is not registered", email);
				return ResponseEntity.status(419).body("user not registered");
			} else {
				// generate random password
				String randomPassword = generateRandomPassword();
				logger.info("Generated random password {}", randomPassword);

				// send email to user sharing random password
				emailSenderService.sendResetPasswordEmail(email, randomPassword);

				// update the DB to use random password for user
				UserInfo userInfo = new UserInfo(email);
				userInfo.setPassword(randomPassword);
				var uinfo = userInfoRepository.save(userInfo);

				logger.info("Updated user {} password to {}", uinfo.getEmail(), randomPassword);
				return ResponseEntity.status(200).body("user updated");
			}
		} catch(Exception e){
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}
	@PostMapping("/emailOTP")
	public ResponseEntity<String> emailOTP(@RequestHeader(value="email") String email) {
		try{
			logger.info("Checking whether email is already registered");
			var val = userInfoRepository.findById(email);
			if (val.isPresent()) {
				logger.info("User {} is already registered", email);
				return ResponseEntity.status(420).body("user already registered");
			}
			String OTP = generateRandomOTP();
			emailSenderService.sendOTPEmail(email, OTP);
			logger.info("Sent OTP {} to {}", OTP, email);
			return ResponseEntity.status(200).body(OTP);
		} catch(Exception e){
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@GetMapping("/")
	public ResponseEntity<String> getAllUsers() {
		StringBuilder sb = new StringBuilder();
    	userInfoRepository.findAll().forEach(sb::append);
		return ResponseEntity.ok(sb.toString());
	}

	static String generateRandomPassword() {
        List<CharacterRule> rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1));
    
        PasswordGenerator generator = new PasswordGenerator();
        String password = generator.generatePassword(8, rules);
        return password;
    }
	static String generateRandomOTP() {
        List<CharacterRule> rules = Arrays.asList(new CharacterRule(EnglishCharacterData.Digit, 5));
    
        PasswordGenerator generator = new PasswordGenerator();
        String password = generator.generatePassword(5, rules);
        return password;
    }
}
