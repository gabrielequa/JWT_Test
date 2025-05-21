package dev.gabrielequa.provajwt;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.gabrielequa.provajwt.model.Role;
import dev.gabrielequa.provajwt.model.User;
import dev.gabrielequa.provajwt.service.UserService;

@SpringBootApplication
public class ProvajwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProvajwtApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "Mario Rossi", "mario", "1234", null, new ArrayList<>()));
			userService.saveUser(new User(null, "Luigi Bianchi", "luigi", "1234", null, new ArrayList<>()));
			userService.saveUser(new User(null, "Laura Gialli", "laura", "1234", null, new ArrayList<>()));
			userService.saveUser(new User(null, "Antonio Blu", "antonio", "1234", null, new ArrayList<>()));

			userService.addRoleToUser("mario", "ROLE_USER");
			userService.addRoleToUser("luigi", "ROLE_MANAGER");
			userService.addRoleToUser("laura", "ROLE_ADMIN");
			userService.addRoleToUser("antonio", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("antonio", "ROLE_ADMIN");
			userService.addRoleToUser("antonio", "ROLE_USER");
		};
	}

}
