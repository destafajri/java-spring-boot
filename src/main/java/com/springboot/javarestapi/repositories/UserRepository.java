package com.springboot.javarestapi.repositories;

import com.springboot.javarestapi.core.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}