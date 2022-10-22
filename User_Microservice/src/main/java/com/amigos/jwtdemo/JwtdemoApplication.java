package com.amigos.jwtdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
public class JwtdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtdemoApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}//when to application run we will have this bean available

/*@Bean
	CommandLineRunner commandLineRunner(UserService userService){
		return args -> {
			userService.saveRole(new Role(null,"ROLE_USER"));

			userService.saveRole(new Role(null,"ROLE_MANAGER"));

			userService.saveRole(new Role(null,"ROLE_ADMIN"));

			userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

			userService.SaveUser(new AppUser(null,"mourad","mourad123","1234",new ArrayList<>()));
			userService.SaveUser(new AppUser(null,"ahmed","ahmed123","1234",new ArrayList<>()));
			userService.SaveUser(new AppUser(null,"bilel","bilel123","1234",new ArrayList<>()));
			userService.SaveUser(new AppUser(null,"saif","saif123","1234",new ArrayList<>()));

			userService.addRoleToUser("mourad123","ROLE_USER");

			userService.addRoleToUser("mourad123","ROLE_MANAGER");

			userService.addRoleToUser("ahmed123","ROLE_MANAGER");

			userService.addRoleToUser("bilel123","ROLE_ADMIN");

			userService.addRoleToUser("saif123","ROLE_USER");
			userService.addRoleToUser("saif123","ROLE_MANAGER");
			userService.addRoleToUser("saif123","ROLE_SUPER_ADMIN");
		};
	}*/
}
