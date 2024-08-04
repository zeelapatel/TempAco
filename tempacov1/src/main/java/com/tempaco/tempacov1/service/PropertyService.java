package com.tempaco.tempacov1.service;

import com.tempaco.tempacov1.dto.PropertyDto;
import com.tempaco.tempacov1.model.Property;
import com.tempaco.tempacov1.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PropertyService {


    private final  PropertyRepository propertyRepository;

    public Property addProperty(PropertyDto propertyDto) throws IOException {
        Property property = new Property();
        property.setTitle(propertyDto.getTitle());
        property.setDescription(propertyDto.getDescription());
        property.setAddress(propertyDto.getAddress());
        property.setPrice(propertyDto.getPrice());
        property.setZip(propertyDto.getZip());
        if(propertyDto.getPhoto()!=null && !propertyDto.getPhoto().isEmpty()){
            property.setPhoto(propertyDto.getPhoto().getBytes());
        }
    return propertyRepository.save(property);
    }
}
