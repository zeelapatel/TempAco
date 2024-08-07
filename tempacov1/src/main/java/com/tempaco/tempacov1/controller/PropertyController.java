package com.tempaco.tempacov1.controller;

import com.tempaco.tempacov1.dto.GetPropertyDto;
import com.tempaco.tempacov1.dto.PropertyDto;
import com.tempaco.tempacov1.dto.SavePropertyResult;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final UserService userService;



    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?>  uploadProperty (@ModelAttribute PropertyDto propertyDto) throws IOException {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String userEmail = authentication.getName();

                Optional<User> userEntityOptional = userService.getUserByEmail(userEmail);
                if (userEntityOptional.isPresent()) {
                    User userEntity = userEntityOptional.get();
                    Long userId = userEntity.getId();

                    var response = propertyService.save(propertyDto,userId);


                    SavePropertyResult result = SavePropertyResult.builder()
                            .error(false).zip(response.getZip()).title(response.getTitle())
                            .description(response.getDescription()).price(response.getPrice())
                            .address(response.getAddress())
                            .build();
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
    }


    @GetMapping("/getProperties")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserProperties() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userEmail = authentication.getName();

            Optional<User> userEntityOptional = userService.getUserByEmail(userEmail);
            if (userEntityOptional.isPresent()) {
                User userEntity = userEntityOptional.get();
                Long userId = userEntity.getId();

                List<Property> properties = propertyService.getPropertiesByUserId(userId);
                List<GetPropertyDto> response = properties.stream().map(property -> new GetPropertyDto(
                        property.getId(),
                        property.getTitle(),
                        property.getDescription(),
                        property.getAddress(),
                        property.getZip(),
                        property.getPrice(),
                        property.getPhoto()
                )).collect(Collectors.toList());

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}