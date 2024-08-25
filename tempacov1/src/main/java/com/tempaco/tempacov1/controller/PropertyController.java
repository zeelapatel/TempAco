package com.tempaco.tempacov1.controller;

import com.tempaco.tempacov1.dto.GetPropertyDto;
import com.tempaco.tempacov1.dto.PropertyDto;
import com.tempaco.tempacov1.dto.SavePropertyResult;
import com.tempaco.tempacov1.model.User;
import com.tempaco.tempacov1.model.Property;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;

import com.tempaco.tempacov1.service.PropertyService;
import com.tempaco.tempacov1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Controller
//@RequestMapping("/api/v1/property")
//@RequiredArgsConstructor
//public class PropertyController {
//
//    private final PropertyService propertyService;
//    private final UserService userService;
//
//
//
//    @PostMapping("/upload")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?>  uploadProperty (@ModelAttribute PropertyDto propertyDto) throws IOException {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication != null && authentication.isAuthenticated()) {
//                String userEmail = authentication.getName();
//
//                Optional<User> userEntityOptional = userService.getUserByEmail(userEmail);
//                if (userEntityOptional.isPresent()) {
//                    User userEntity = userEntityOptional.get();
//                    Long userId = userEntity.getId();
//
//                    var response = propertyService.save(propertyDto,userId);
//
//
//                    SavePropertyResult result = SavePropertyResult.builder()
//                            .error(false).zip(response.getZip()).title(response.getTitle())
//                            .description(response.getDescription()).price(response.getPrice())
//                            .address(response.getAddress())
//                            .build();
//                    return new ResponseEntity<>(result, HttpStatus.OK);
//                }else{
//                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//                }
//            }else {
//                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//            }
//    }
//
//
//    @GetMapping("/getProperties")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> getUserProperties() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            String userEmail = authentication.getName();
//
//            Optional<User> userEntityOptional = userService.getUserByEmail(userEmail);
//            if (userEntityOptional.isPresent()) {
//                User userEntity = userEntityOptional.get();
//                Long userId = userEntity.getId();
//
//                List<Property> properties = propertyService.getPropertiesByUserId(userId);
//                List<GetPropertyDto> response = properties.stream().map(property -> new GetPropertyDto(
//                        property.getId(),
//                        property.getTitle(),
//                        property.getDescription(),
//                        property.getAddress(),
//                        property.getZip(),
//                        property.getPrice(),
//                        property.getPhoto()
//                )).collect(Collectors.toList());
//
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } else {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//}

@Controller
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    private Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        String userEmail = authentication.getName();
        Optional<User> userEntityOptional = userService.getUserByEmail(userEmail);
        return userEntityOptional;
    }



    @PostMapping("/addProperty")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> uploadProperty(@ModelAttribute PropertyDto propertyDto) throws IOException {
        Optional<User> userOptional = getAuthenticatedUser();
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();
        var response = propertyService.save(propertyDto, user.getId());

        SavePropertyResult result = SavePropertyResult.builder()
                .error(false)
                .zip(response.getZip())
                .title(response.getTitle())
                .description(response.getDescription())
                .price(response.getPrice())
                .address(response.getAddress())
                .moveInDate(response.getMoveInDate())
                .moveOutDate(response.getMoveOutDate())
                .bath(response.getBath())
                .bed(response.getBed())
                .build();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/userProperties")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserProperties() {
        Optional<User> userOptional = getAuthenticatedUser();
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();
        List<GetPropertyDto> response = propertyService.getPropertiesByUserId(user.getId())
                .stream()
                .map(property -> new GetPropertyDto(
                        property.getId(),
                        property.getTitle(),
                        property.getDescription(),
                        property.getAddress(),
                        property.getZip(),
                        property.getPrice(),
                                property.getBed(),
                                property.getBath(), property.getMoveInDate(),
                                property.getMoveOutDate(),
                        property.getPhoto())
                        )
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/listing")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> listing(
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer bed,
            @RequestParam(required = false) Double bath,
            @RequestParam(required = false) Date moveInDate,
            @RequestParam(required = false) Date moveOutDate) {

        List<GetPropertyDto> getPropertyDto = propertyService.getListing(price,bed,bath,moveInDate,moveOutDate);

        return ResponseEntity.ok(getPropertyDto);
    }

    @PutMapping("/updateProperty")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateProperty(@ModelAttribute PropertyDto propertyDto) throws IOException {

        Optional<User> userOptional = getAuthenticatedUser();
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userOptional.get();

        var response = propertyService.updateProperty(propertyDto,user.getId());
        if(response){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



}