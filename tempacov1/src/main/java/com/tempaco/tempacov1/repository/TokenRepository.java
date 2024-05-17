package com.tempaco.tempacov1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tempaco.tempacov1.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    
    @Query("""
            select t from Token t where t.token ilike concat(:token, '%')
            """)
    Optional<Token> findByToken(String token);
    
    @Query("""
            select t from Token t inner join t.user u
            where u.id = :userId and (t.expired = false or t.revoked = false)
            """)
    List<Token> findAllValidTokenByUser(Long userId);
}
