package com.tempaco.tempacov1.controller;

import com.tempaco.tempacov1.dto.PropertyDto;
import com.tempaco.tempacov1.model.Property;
import com.tempaco.tempacov1.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("/addProperty")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Property> addProperty(@ModelAttribute PropertyDto propertyDto) {
        try {
            Property savedProperty = propertyService.addProperty(propertyDto);
            return new ResponseEntity<>(savedProperty, HttpStatus.CREATED);

        }catch (IOException e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
