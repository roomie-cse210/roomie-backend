package com.roomie.roomie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RoomieApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomieApplication.class, args);
	}

}
