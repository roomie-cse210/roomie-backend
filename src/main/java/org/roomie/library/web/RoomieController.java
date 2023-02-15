package org.roomie.library.web;

// import java.util.ArrayList;
import java.util.Collection;
// import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.roomie.library.data.model.UserInfo;
import org.roomie.library.data.repositories.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoomieController {

	private static final Logger logger = LoggerFactory.getLogger( RoomieController.class);

	@Autowired
	UserInfoRepository userInfoRepository;

	@GetMapping("/")
	public String dummy() {
		logger.info("Adding the book ");
		return "Welcome to Roomie.com";
	}

	@PostMapping("/books")
	public UserInfo newBookInfo(@RequestBody UserInfo bookInfo) {
		logger.info("Adding the book " + bookInfo);
		return userInfoRepository.save( bookInfo );
	}

	// @GetMapping("/books/{id}")
	// @ResponseBody
	// public UserInfo getBookInfo(@PathVariable final String id) {
	// 	logger.info("Finding the book with id " + id);
	// 	return userInfoRepository.findById(id);
	// 			// .orElseThrow(() -> new BookInfoNotFoundException( id));
	// }
	@GetMapping("/users")
	@ResponseBody
	public Collection<UserInfo> allBookInfos() {
		logger.info("Searching all users");
		return StreamSupport.stream( userInfoRepository.findAll().spliterator(), false)
				.collect( Collectors.toList());

	}
}
