package com.springboot.javarestapi.repositories;

import com.springboot.javarestapi.core.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT * FROM users WHERE id = :user_id", nativeQuery = true)
    UserEntity findByIdUserIdAuthor(
            @Param("user_id") UUID userId);
}