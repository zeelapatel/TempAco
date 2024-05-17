package com.tempaco.tempacov1.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tempaco.tempacov1.model.Role;
import com.tempaco.tempacov1.model.User;
import com.tempaco.tempacov1.repository.UserRepository;
import com.tempaco.tempacov1.service.UserService;

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
