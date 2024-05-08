package com.tempaco.authentication.authentication.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tempaco.authentication.authentication.repository.UserRepository;
import com.tempaco.authentication.authentication.service.UserService;
import com.tempaco.authentication.authentication.model.Role;
import com.tempaco.authentication.authentication.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	@Override
	public void run(String... args) throws Exception {
		
		if(userRepository.count()==0) {
			User admin=User.builder().firstName("admin").lastName("admin").email("admin@admin.com")
					.password(passwordEncoder.encode("admin")).role(Role.ROLE_ADMIN).build();
		userService.save(admin);
		log.debug("created admin user - {}", admin);
		}
		
		
	}

}
