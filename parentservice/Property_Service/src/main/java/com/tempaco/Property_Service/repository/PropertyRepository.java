package com.tempaco.Property_Service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tempaco.Property_Service.entity.Property;
import com.tempaco.authentication.model.User;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByUser(User user);
    Property save(Property property);
    Optional<Property> findById(Long id);
    void deleteById(Long id);
}
