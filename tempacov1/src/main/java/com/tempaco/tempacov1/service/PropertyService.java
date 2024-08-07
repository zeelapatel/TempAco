package com.tempaco.tempacov1.service;

import com.tempaco.tempacov1.dto.PropertyDto;
import com.tempaco.tempacov1.model.Property;
import com.tempaco.tempacov1.model.User;
import com.tempaco.tempacov1.repository.PropertyRepository;
import com.tempaco.tempacov1.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {


    private final  PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    // get all property of one user

    public List<Property> getPropertiesByUserId(Long userId) {
        return propertyRepository.findByUserId(userId);
    }

    // Add Property

    public Property save(PropertyDto propertyDto,Long userId) throws IOException {
        User user1 = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        var property = Property.builder().title(propertyDto.getTitle()).description(propertyDto.getDescription()).zip(propertyDto.getZip())
                .price(propertyDto.getPrice()).address(propertyDto.getAddress()).photo(propertyDto.getPhoto().getBytes()).user(user1)
                .build();

        return propertyRepository.save(property);
    }
}
