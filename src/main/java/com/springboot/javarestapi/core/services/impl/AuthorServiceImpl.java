package com.springboot.javarestapi.core.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.javarestapi.core.domain.dto.AuthorCreateRequestDTO;
import com.springboot.javarestapi.core.domain.dto.AuthorListResponse;
import com.springboot.javarestapi.core.domain.entities.AuthorEntity;
import com.springboot.javarestapi.core.domain.entities.UserEntity;
import com.springboot.javarestapi.core.services.AuthorService;
import com.springboot.javarestapi.repositories.AuthorRepository;
import com.springboot.javarestapi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public void createNewAuthor(AuthorCreateRequestDTO payload) {
        UserEntity user = new UserEntity();
        AuthorEntity author = new AuthorEntity();

        user.setEmail(payload.getEmail());
        user.setUsername(payload.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(payload.getPassword()));
        user.setRole("author");
        user.setActive(true);
        user.setCreatedAt(Timestamp.valueOf(java.time.LocalDateTime.now()));
        author.setUserId(user);
        author.setName(payload.getName());

        userRepository.save(user);
        authorRepository.save(author);
    }

    @Override
    public List<AuthorListResponse> getListAuthor() {
        List<LinkedHashMap<String, Object>> authors = authorRepository.getListAuthor();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(authors);
            List<AuthorListResponse> listAuthor = objectMapper.readValue(jsonString, new TypeReference<>() {
            });

            return listAuthor;
        } catch (JsonProcessingException e) {
            // handle exception
            return (List<AuthorListResponse>) e;
        }
    }
}
