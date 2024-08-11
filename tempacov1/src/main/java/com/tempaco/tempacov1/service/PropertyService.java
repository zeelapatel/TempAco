package com.tempaco.tempacov1.service;

import com.tempaco.tempacov1.dto.GetPropertyDto;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
                .moveInDate(propertyDto.getMoveInDate()).moveOutDate(propertyDto.getMoveOutDate())
                .bed(propertyDto.getBed()).bath(propertyDto.getBath()).build();

        return propertyRepository.save(property);
    }

    // get all listing
    public List<GetPropertyDto> getListing( Double  price, Integer bed, Double bath, Date moveInDate, Date moveOutDate) {

        List<Property> allProperties = propertyRepository.findAll();

        List<Property> filteredProperties = allProperties.stream().filter(property -> price == null || property.getPrice() <= price)
                .filter(property -> bath == null || property.getBath() == bath)
                .filter(property -> bed == null || property.getBed() == bed)
                .filter(property -> moveInDate == null || property.getMoveInDate().equals(moveInDate))
                .filter(property -> moveOutDate==null ||  property.getMoveOutDate().equals(moveOutDate)).toList();

        return filteredProperties.stream().map(property -> new GetPropertyDto(property.getId(),property.getTitle(),property.getDescription()
        ,property.getAddress(),property.getZip(),property.getPrice(),property.getBed(),property.getBath(),property.getMoveInDate(),
                property.getMoveOutDate(),property.getPhoto())).toList();

    }
}
