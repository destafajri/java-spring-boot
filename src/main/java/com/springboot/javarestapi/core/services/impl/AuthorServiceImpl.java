package com.springboot.javarestapi.core.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.javarestapi.common.JsonBuildObjectConverter;
import com.springboot.javarestapi.core.domain.dto.AuthorCreateRequestDTO;
import com.springboot.javarestapi.core.domain.dto.AuthorListResponse;
import com.springboot.javarestapi.core.domain.dto.ResponseData;
import com.springboot.javarestapi.core.domain.entities.AuthorEntity;
import com.springboot.javarestapi.core.domain.entities.UserEntity;
import com.springboot.javarestapi.core.services.AuthorService;
import com.springboot.javarestapi.exception.InternalServerErrorException;
import com.springboot.javarestapi.metadata.Metadata;
import com.springboot.javarestapi.metadata.Pagintation;
import com.springboot.javarestapi.repositories.AuthorRepository;
import com.springboot.javarestapi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
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
    public ResponseData.WithMeta<List<AuthorListResponse>> getListAuthor(Metadata meta) {
        Sort sort = Sort.by(new Sort.Order(Pagintation.getOrderBy(meta.getOrderBy()), meta.getSortBy()));
        Pageable pageable = PageRequest.of(meta.getPageForQuery(), meta.getPerPage(), sort);
        meta.setTotal(authorRepository.totalAuthor());

        List<LinkedHashMap<String, Object>> authors = authorRepository.getListAuthorNativeQuery(pageable.getPageNumber(), pageable.getPageSize(), String.valueOf(pageable.getSort()).replace(":", ""));
        JsonBuildObjectConverter<AuthorListResponse> data = new JsonBuildObjectConverter<>();

        return Pagintation.createResultWithMetaDTO(meta.getPage(), meta.getPerPage(), meta.getTotal(), meta.getSortBy(), meta.getOrderBy(), "Success get list author", data.ConvertToJavaObject(authors));
    }
}
