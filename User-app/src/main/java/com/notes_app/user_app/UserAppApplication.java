package com.notes_app.user_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.notes_app.user_app.models")
@SpringBootApplication(scanBasePackages = "com.notes_app.user_app")
@EnableJpaRepositories("com.notes_app.user_app.repositories")
public class UserAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAppApplication.class, args);
	}

}
