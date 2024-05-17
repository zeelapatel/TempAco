package com.tempaco.tempacov1.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.oauth2.login.UserInfoEndpointDsl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.PutExchange;

import com.tempaco.tempacov1.dto.UserResponseDto;
import com.tempaco.tempacov1.model.User;
import com.tempaco.tempacov1.repository.UserRepository;
import com.tempaco.tempacov1.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// Get User endpoint By userId
	
	@GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("userId") Long userId) {
        if (userId != null) {
            Optional<User> userEntity = userService.getUserById(userId);
            if (userEntity.isPresent()) {
                UserResponseDto userResponseDto = mapToUserResponseDto(userEntity.get());
                return ResponseEntity.ok(userResponseDto);
            } else {
                return ResponseEntity.notFound().build(); // Or return an appropriate error response
            }
        } else {
            return ResponseEntity.badRequest().build(); // Or return an appropriate error response
        }
    }

    private UserResponseDto mapToUserResponseDto(User user) {
        return UserResponseDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
    
    //update user informattion endpoint
    
    @PutMapping("/users/updateUserInfo")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponseDto> updateUserInformation( @RequestBody UserResponseDto userResponseDto){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

    	UserResponseDto updatedUser = userService.updateUserInformation(userEmail,userResponseDto);
    	 if (updatedUser != null) {
             return ResponseEntity.ok(updatedUser);
         } else {
             return ResponseEntity.notFound().build(); // Or return an appropriate error response
         }
    
    }
    
    
    
}
