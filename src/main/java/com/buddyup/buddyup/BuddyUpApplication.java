package com.buddyup.buddyup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BuddyUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuddyUpApplication.class, args);
	}

}
