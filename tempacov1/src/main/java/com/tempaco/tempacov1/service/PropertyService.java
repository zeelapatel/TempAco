package com.tempaco.tempacov1.service;

import com.tempaco.tempacov1.dto.PropertyDto;
import com.tempaco.tempacov1.model.Property;
import com.tempaco.tempacov1.model.User;
import com.tempaco.tempacov1.repository.PropertyRepository;
import com.tempaco.tempacov1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PropertyService {


    private final  PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public Property addProperty(PropertyDto propertyDto, Long userId) throws IOException {
        Property property = new Property();
        property.setTitle(propertyDto.getTitle());
        property.setDescription(propertyDto.getDescription());
        property.setAddress(propertyDto.getAddress());
        property.setPrice(propertyDto.getPrice());
        property.setZip(propertyDto.getZip());
        if(propertyDto.getPhoto()!=null && !propertyDto.getPhoto().isEmpty()){
            property.setPhoto(propertyDto.getPhoto().getBytes());
        }
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        property.setUser(user);


    return propertyRepository.save(property);
    }
}
