package com.tempaco.authentication.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tempaco.authentication.dto.UserResponseDto;
import com.tempaco.authentication.model.User;
import com.tempaco.authentication.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;

	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) {
				return userRepository.findByEmail(username)
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			}
		};
	}

	public User save(User newUser) {
		if (newUser.getId() == null) {
			newUser.setCreatedAt(LocalDateTime.now());
		}

		newUser.setUpdatedAt(LocalDateTime.now());
		return userRepository.save(newUser);
	}

	public Optional<User> getUserById(Long userId) {
        if (userId != null) {
            return userRepository.findById(userId);
        }
        return Optional.empty();
    }

	public UserResponseDto updateUserInformation(String userEmail, UserResponseDto userResponseDto) {
		
		Optional<User> optionalUser= userRepository.findByEmail(userEmail);
		if(optionalUser.isPresent()) {
			User user=optionalUser.get();
			
			
			user.setFirstName(userResponseDto.getFirstName());
			user.setLastName(userResponseDto.getLastName());
			user.setEmail(userResponseDto.getEmail());
			User updatedUser = userRepository.save(user);
			UserResponseDto response =  UserResponseDto.builder()
					.firstName(updatedUser.getFirstName())
					.lastName(updatedUser.getLastName())
					.email(updatedUser.getEmail())
					.build();
		return response;
		}
		
		
		return null;
	}
}