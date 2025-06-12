package com.side.subscribernews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubscriberNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriberNewsApplication.class, args);
	}

}
