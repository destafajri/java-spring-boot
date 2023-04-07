package com.springboot.javarestapi.core.services.impl;

import com.springboot.javarestapi.core.domain.dto.AuthorCreateRequestDTO;
import com.springboot.javarestapi.core.domain.entities.AuthorEntity;
import com.springboot.javarestapi.core.domain.entities.UserEntity;
import com.springboot.javarestapi.core.services.AuthorService;
import com.springboot.javarestapi.repositories.AuthorRepository;
import com.springboot.javarestapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void createNewAuthor(AuthorCreateRequestDTO payload) {
        UserEntity user = new UserEntity();
        AuthorEntity author = new AuthorEntity();
        user.setEmail(payload.getEmail());
        user.setUsername(payload.getUsername());
        user.setPassword(payload.getPassword());
        user.setRole("author");
        user.set_active(true);
        user.setCreated_at(Timestamp.valueOf(java.time.LocalDateTime.now()));
        author.setUserId(user);
        author.setName(payload.getName());

        userRepository.save(user);
        authorRepository.save(author);
    }
}
