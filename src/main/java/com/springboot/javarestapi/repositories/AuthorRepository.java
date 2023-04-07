package com.springboot.javarestapi.repositories;

import com.springboot.javarestapi.core.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {
}