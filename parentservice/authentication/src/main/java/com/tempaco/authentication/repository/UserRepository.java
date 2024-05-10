package com.tempaco.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tempaco.authentication.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>  findByEmail(String email);
}