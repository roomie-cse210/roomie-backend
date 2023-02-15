package com.roomie.roomie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan("com.roomie.roomie")
public class RoomieApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomieApplication.class, args);
	}

}
