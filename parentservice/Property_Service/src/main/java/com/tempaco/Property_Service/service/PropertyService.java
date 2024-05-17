package com.tempaco.Property_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tempaco.Property_Service.entity.Property;
import com.tempaco.Property_Service.repository.PropertyRepository;
import com.tempaco.authentication.model.User;


import jakarta.servlet.http.HttpServletRequest;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    
   

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Long propertyId) {
        return propertyRepository.findById(propertyId).orElse(null);
    }
    

    public List<Property> getPropertiesByUser(User user) {
        return propertyRepository.findByUser(user);
    }

    public Property createProperty(Property property, User user) {
        property.setUser(user);
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long propertyId, Property updatedProperty) {
        Property property = propertyRepository.findById(propertyId).orElse(null);
        if (property != null) {
            property.setName(updatedProperty.getName());
            property.setLocation(updatedProperty.getLocation());
            property.setPrice(updatedProperty.getPrice());
            return propertyRepository.save(property);
        }
        return null;
    }

    public void deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
    }
}
