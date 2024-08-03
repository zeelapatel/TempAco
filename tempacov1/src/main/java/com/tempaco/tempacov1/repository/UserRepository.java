package com.tempaco.tempacov1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tempaco.tempacov1.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>  findByEmail(String email);
    Optional<User>  findById(Long id);
    User findUserByEmail(String email);
}