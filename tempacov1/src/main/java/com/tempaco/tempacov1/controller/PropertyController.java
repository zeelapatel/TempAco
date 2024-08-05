package com.tempaco.tempacov1.controller;

import com.tempaco.tempacov1.dto.PropertyDto;
import com.tempaco.tempacov1.model.Property;
import com.tempaco.tempacov1.model.User;
import com.tempaco.tempacov1.service.PropertyService;
import com.tempaco.tempacov1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final UserService userService;
    @PostMapping("/addProperty")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addProperty(@ModelAttribute PropertyDto propertyDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userEmail = authentication.getName();

            Optional<User> userEntityOptional = userService.getUserByEmail(userEmail);
            if (userEntityOptional.isPresent()) {
                User userEntity = userEntityOptional.get();
                Long userId = userEntity.getId();

                try {
                    Property savedProperty = propertyService.addProperty(propertyDto, userId);
                    return new ResponseEntity<>(savedProperty, HttpStatus.CREATED);
                } catch (IOException e) {
                    return new ResponseEntity<>("Error processing property data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }
}