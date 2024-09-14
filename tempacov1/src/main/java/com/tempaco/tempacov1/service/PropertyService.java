package com.tempaco.tempacov1.service;

import com.tempaco.tempacov1.dto.GetPropertyDto;
import com.tempaco.tempacov1.dto.PropertyDto;
import com.tempaco.tempacov1.dto.PropertySpecifications;
import com.tempaco.tempacov1.model.Property;
import com.tempaco.tempacov1.model.User;
import com.tempaco.tempacov1.repository.PropertyRepository;
import com.tempaco.tempacov1.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class PropertyService {


    private final  PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ServerProperties serverProperties;

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

    public boolean updateProperty(PropertyDto propertyDto,Long id) throws IOException {
        Property property=propertyRepository.findById(propertyDto.getId()).orElseThrow(()->new RuntimeException("Property not found"));

        if(id.equals( property.getUser().getId())) {
            property.setBed(propertyDto.getBed());
            property.setAddress(propertyDto.getAddress());
            property.setBath(propertyDto.getBath());
            property.setPrice(propertyDto.getPrice());
            property.setTitle(propertyDto.getTitle());
            property.setDescription(propertyDto.getDescription());
            property.setMoveInDate(propertyDto.getMoveInDate());
            property.setMoveOutDate(propertyDto.getMoveOutDate());
            property.setZip(propertyDto.getZip());
            property.setPhoto(propertyDto.getPhoto().getBytes());

            propertyRepository.save(property);
            return true;
        }else{
            log.error("property not belong to user");
            return false;

        }
    }



    public List<Property> searchProperties(String location, Double minPrice, Double maxPrice, Integer bed, Double bath, Date moveInDate, Date moveOutDate) {
        Specification<Property> spec = Specification.where(null);

        spec = spec.and(PropertySpecifications.hasLocation(location))
                .and(PropertySpecifications.hasMinPrice(minPrice))
                .and(PropertySpecifications.hasMaxPrice(maxPrice))
                .and(PropertySpecifications.hasBed(bed))
                .and(PropertySpecifications.hasBath(bath))
                .and(PropertySpecifications.hasMoveInDate(moveInDate))
                .and(PropertySpecifications.hasMoveOutDate(moveOutDate));

        return propertyRepository.findAll(spec);
    }
}
