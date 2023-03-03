package org.roomie.library.web;

import org.roomie.library.data.model.RoomieProfile;
import org.roomie.library.data.model.UserFilters;
import org.roomie.library.data.model.RoomieProfileFilterRequest;
import org.roomie.library.data.model.UserInfo;
import org.roomie.library.data.model.RoomieRequest;
import org.roomie.library.data.model.RoomieRequestKey;
import org.roomie.library.data.repositories.UserInfoRepository;
import org.roomie.library.data.repositories.RoomieProfileRespository;
import org.roomie.library.data.repositories.UserFiltersRepository;
import org.roomie.library.data.repositories.RoomieRequestRepository;
import org.roomie.library.data.services.EmailSenderService;
import org.roomie.library.data.services.SecureKeysService;
import org.roomie.library.data.services.AmazonClient;
import org.roomie.library.data.services.UserFiltersService;
import org.roomie.library.data.services.DynamoDbRequestService;
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
import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.util.Arrays;
import java.io.File;
import java.util.*;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;  

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RoomieController {

	private static final Logger logger = LoggerFactory.getLogger( RoomieController.class);
	private AmazonClient amazonClient;

	@Autowired
    RoomieController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	RoomieProfileRespository roomieProfileRespository;

	@Autowired
	RoomieRequestRepository roomieRequestRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private SecureKeysService secureKeysService;

	@Autowired
	private DynamoDbRequestService dynamoDbRequestService;

	@Autowired
	private UserFiltersRepository userFiltersRepository;

	@Autowired
	private UserFiltersService userFiltersService;

	@PostMapping("/verifyUser")
	public ResponseEntity<String> verifyUser(@RequestBody UserInfo userInfo) throws Exception {
		userInfo = encryptPassword(userInfo);
		try {
			logger.info("Checking login credentials");
			var val = userInfoRepository.findById(userInfo.getEmail());
			if (val.isPresent()) {
				var uinfo = val.get();
				if (uinfo.getPassword().equals(userInfo.getPassword())) {
					logger.info("Login Successful");
					return ResponseEntity.status(200).body("login successful");
				} else {
					logger.info("Login Failed Due to Incorrect Password.");
					return ResponseEntity.status(401).body("incorrect password");
				}
			} else {
				logger.info("No user found with username {}", userInfo.getEmail());
				return ResponseEntity.status(419).body("user not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/createUser")
	public ResponseEntity<String> createUser(@RequestBody UserInfo userInfo) throws Exception {
		userInfo = encryptPassword(userInfo);
		try {
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
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody UserInfo userInfo) throws Exception {
		userInfo = encryptPassword(userInfo);
		try {
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
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestHeader(value = "email") String email) {
		logger.info("Checking whether email is registered");
		try {
			var val = userInfoRepository.findById(email);

			if (!val.isPresent()) {
				logger.info("User {} is not registered", email);
				return ResponseEntity.status(419).body("user not registered");
			} else {
				String OTP = generateRandomOTP();
				emailSenderService.sendForgotPasswordOTPEmail(email, OTP);
				logger.info("Sent Forgot Password Code {} to {}", OTP, email);
				return ResponseEntity.status(200).body(OTP);
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/emailOTP")
	public ResponseEntity<String> emailOTP(@RequestHeader(value = "email") String email) {
		try {
			logger.info("Checking whether email is already registered");
			var val = userInfoRepository.findById(email);
			if (val.isPresent()) {
				logger.info("User {} is already registered", email);
				return ResponseEntity.status(420).body("user already registered");
			}
			String OTP = generateRandomOTP();
			emailSenderService.sendSignupOTPEmail(email, OTP);
			logger.info("Sent Signup Code {} to {}", OTP, email);
			return ResponseEntity.status(200).body(OTP);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/createRoomieProfile")
	public ResponseEntity<String> createRoomieProfile(@RequestBody RoomieProfile roomieProfile) throws Exception {
		try {
			var val = roomieProfileRespository.findById(roomieProfile.getEmail());
			String uniquePhotoName = roomieProfile.getEmail();

			if (val.isPresent()) {
				//TODO  this.amazonClient.delete/overwrite	
				String returnedURL = this.amazonClient.uploadFile(roomieProfile.getPhotoData(), uniquePhotoName);
				// TODO
				roomieProfile.setPhotoURL(returnedURL);
				logger.info("profile photo {} is rendered", roomieProfile.getPhotoURL());

				roomieProfileRespository.save(roomieProfile);
				logger.info("roomie profile {} is updated", roomieProfile.getEmail());
				return ResponseEntity.status(200).body("roomie profile updated");

			} else {
				// logger.info(roomieProfile.photoStruct.photoData.getClass().getSimpleName());
				String returnedURL = this.amazonClient.uploadFile(roomieProfile.getPhotoData(), uniquePhotoName);
				roomieProfile.setPhotoURL(returnedURL);
				logger.info("profile photo {} is rendered", roomieProfile.getPhotoURL());

				var roomieinfo = roomieProfileRespository.save(roomieProfile);
				logger.info("Created roomie profile {} successfully", roomieinfo.getEmail());
				userFiltersService.getMatchingFilterUserEmail(roomieProfile);
				return ResponseEntity.status(200).body("roomie profile created");

			}
		} catch (Exception e) {
			logger.info("error:", e);
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	private void uploadFileTos3bucket(String fileName, File file) {

	}

	@PostMapping("/getRoomieProfile")
	public ResponseEntity<String> getRoomieProfile(@RequestHeader(value = "email") String email) {
		String jsonStr = new String();
		try {
			jsonStr = dynamoDbRequestService.getUserProfile(email);
			logger.info("roomie profile {} is fetched", email);
			return ResponseEntity.status(200).body(jsonStr);

		} catch (Exception e) {
			logger.info("error:", e);
			jsonStr = "Internal Server Error";
			return ResponseEntity.status(500).body(jsonStr);
		}
	}

	@PostMapping("/getAllRoomieProfiles")
	public ResponseEntity<String> getAllRoomieProfiles() {
		List<String> jsonStr = new ArrayList<String>();
		try {
			jsonStr = dynamoDbRequestService.getAllUserProfiles();
			logger.info("all roomie profile {} is fetched");
			return ResponseEntity.status(200).body(jsonStr.toString());

		} catch (Exception e) {
			logger.info("error:", e);
			jsonStr.add("Internal Server Error");
			return ResponseEntity.status(500).body(jsonStr.toString());
		}
	}

	@PostMapping("/getRoomieProfilesBasedOnFilters")
	public ResponseEntity<String> getRoomieProfilesBasedOnFilters(
			@RequestBody RoomieProfileFilterRequest roomieProfileFilterRequest) {
		List<String> jsonStr = new ArrayList<String>();
		try {
			jsonStr = dynamoDbRequestService.getFilteredRecords(roomieProfileFilterRequest);
			return ResponseEntity.status(200).body(jsonStr.toString());

		} catch (Exception e) {
			logger.info("error:", e);
			jsonStr.add("Internal Server Error");
			return ResponseEntity.status(500).body(jsonStr.toString());
		}
	}

	@PostMapping("/sendEmailInvite")
	public ResponseEntity<String> sendEmailInvite(@RequestHeader(value = "requestSenderEmail") String requestSenderEmail,
			@RequestHeader(value = "requestReceiverEmail") String requestReceiverEmail,
			@RequestHeader(value = "message") String message) {
		try {
			// find sender
			var val = roomieProfileRespository.findById(requestSenderEmail);
			// if sender exists
			if (val.isPresent()) {
				// get sender's profile
				RoomieProfile profile = val.get();
				// call service
				emailSenderService.sendEmailInvite(requestSenderEmail, requestReceiverEmail, profile.getName(), message);
				try{
					// save request xs
					var roomieRequestKey = new RoomieRequestKey(requestSenderEmail, requestReceiverEmail);
					var request = roomieRequestRepository.save(new RoomieRequest(roomieRequestKey, message, "P"));
					logger.info("Sent roomie request {} successfully", request.getRequestSenderEmail());
				} catch(Exception e){
					return ResponseEntity.status(500).body("Internal Server Error");
				}
				return ResponseEntity.status(200).body("request sent");
			} else {
				logger.info("User {} is not registered", requestSenderEmail);
				return ResponseEntity.status(419).body("user not registered");
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/saveFilter")
	public ResponseEntity<String> saveFilter(@RequestBody UserFilters userFilters) {
		try{
			userFilters.setId(UUID.randomUUID().toString());
			var userFilter = userFiltersRepository.save(userFilters);
			logger.info("Created user filter for user {} successfully", userFilter.getEmail());
			return ResponseEntity.status(200).body("user filter created");
			 
		} catch(Exception e){
			logger.info("error:",e);
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/createAlertOnFilter")
	public ResponseEntity<String> createAlertOnFilter(@RequestBody UserFilters userFilters) {
		try{
			userFilters.setId(UUID.randomUUID().toString());
			var userFilter = userFiltersRepository.save(userFilters);
			logger.info("Created user filter for user {} successfully", userFilter.getEmail());
			return ResponseEntity.status(200).body("user filter created");
			 
		} catch(Exception e){
			logger.info("error:",e);
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/rejectRequest")
	public ResponseEntity<String> rejectRequest(@RequestHeader(value = "requestSenderEmail") String requestSenderEmail,
			@RequestHeader(value = "requestReceiverEmail") String requestReceiverEmail) {
		try {
			// find request
			var request = dynamoDbRequestService.getConnectionRequest(new RoomieRequestKey(requestSenderEmail, requestReceiverEmail));
			request.setStatus("R");
			roomieRequestRepository.save(request);
			logger.info("Reject roomie request {} successfully");
			return ResponseEntity.status(200).body("reject Request");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/acceptRequest")
	public ResponseEntity<String> acceptRequest(@RequestHeader(value = "requestSenderEmail") String requestSenderEmail,
			@RequestHeader(value = "requestReceiverEmail") String requestReceiverEmail) {
		try {
			// find request
			var request = dynamoDbRequestService.getConnectionRequest(new RoomieRequestKey(requestSenderEmail, requestReceiverEmail));
			request.setStatus("A");
			roomieRequestRepository.save(request);
			logger.info("Accept roomie request {} successfully");
			return ResponseEntity.status(200).body("accept Request");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@PostMapping("/getConnections")
	@ResponseBody
	public ResponseEntity<String> getConnections(@RequestHeader(value = "email") String email) {
		try {
			Map<String, Object> result;
			result = dynamoDbRequestService.getConnections(email);
			logger.info("get roomie connections of user successfully");
			String jsonStr=new ObjectMapper().writeValueAsString(result);
			return ResponseEntity.status(200).body(jsonStr);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}

	@GetMapping("/")
	public ResponseEntity<String> getAllUsers() {
		StringBuilder sb = new StringBuilder();
		userInfoRepository.findAll().forEach(sb::append);
		return ResponseEntity.ok(sb.toString());
	}

	static String generateRandomOTP() {
		List<CharacterRule> rules = Arrays.asList(new CharacterRule(EnglishCharacterData.Digit, 5));

		PasswordGenerator generator = new PasswordGenerator();
		String password = generator.generatePassword(5, rules);
		return password;
	}

	private UserInfo encryptPassword(UserInfo userInfo) throws Exception {
		String currentPass = userInfo.getPassword();
		userInfo.setPassword(secureKeysService.hashPassword(currentPass));
		return userInfo;
	}

}
