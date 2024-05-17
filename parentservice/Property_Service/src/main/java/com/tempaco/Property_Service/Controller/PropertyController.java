package com.tempaco.Property_Service.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tempaco.Property_Service.entity.Property;
import com.tempaco.Property_Service.service.PropertyService;
import com.tempaco.authentication.dto.UserResponseDto;
import com.tempaco.authentication.model.User;
import com.tempaco.authentication.service.AuthenticationService;
import com.tempaco.authentication.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;


    @Autowired
    private AuthenticationService authenticationService; // Assuming you have a service for authentication

    @GetMapping("/getallproperties")
    public List<Property> getAllProperties(HttpServletRequest request) {
        // Extract the JWT from the request
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        // Use the user details to get properties
        return propertyService.getAllProperties();
    }

    @PostMapping("/createproperties")
    public Property createProperty(@RequestBody Property property) {
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the user's email from the authentication object
        String userEmail = authentication.getName();

     // Assuming you have the email stored in a variable userEmail
        User user = (User) userService.userDetailsService().loadUserByUsername(userEmail);


        // Use the user object to create the property
        return propertyService.createProperty(property, user);
    }

    // Other methods for updating and deleting properties
}
