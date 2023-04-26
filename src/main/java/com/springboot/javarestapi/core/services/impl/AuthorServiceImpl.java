package com.springboot.javarestapi.core.services.impl;

import com.springboot.javarestapi.common.JsonBuildObjectConverter;
import com.springboot.javarestapi.core.domain.dto.*;
import com.springboot.javarestapi.core.domain.entities.AuthorEntity;
import com.springboot.javarestapi.core.domain.entities.UserEntity;
import com.springboot.javarestapi.core.services.AuthorService;
import com.springboot.javarestapi.exception.BadRequestException;
import com.springboot.javarestapi.metadata.Metadata;
import com.springboot.javarestapi.metadata.PagintationUtility;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

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
    @Transactional
    public ResponseData.WithMeta<List<AuthorListResponse>> getListAuthor(Metadata meta) {
        Sort sort = Sort.by(new Sort.Order(PagintationUtility.getOrderBy(meta.getOrderBy()), meta.getSortBy()));
        Pageable pageable = PageRequest.of(meta.getPageForQuery(), meta.getPerPage(), sort);
        /**
         * To do: Sorted doesn't work for native query
         */
        List<LinkedHashMap<Object, Object>> authors = authorRepository.getListAuthorNativeQuery(pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize());
        JsonBuildObjectConverter<AuthorListResponse> data = new JsonBuildObjectConverter<>();

        meta.setTotal(authorRepository.totalAuthor());
        return ResponseUtility.createResultWithMetaDTO(meta.getPage(), meta.getPerPage(), meta.getTotal(), meta.getSortBy(), meta.getOrderBy(), "Success get list author", data.ConvertListToJavaObject(authors));
    }

    @Override
    public ResponseData<AuthorDetailResponse> getDetailAuthor(UUID id) {
        LinkedHashMap<Object, Object> authorMap = authorRepository.getDetailAuthorNativeQuery(id);

        AuthorDetailResponse authorDetailResponse = new AuthorDetailResponse();

        authorDetailResponse.setId(UUID.fromString((String) authorMap.get("id")));
        authorDetailResponse.setUserId(UUID.fromString((String) authorMap.get("user_id")));
        authorDetailResponse.setUsername((String) authorMap.get("username"));
        authorDetailResponse.setName((String) authorMap.get("name"));
        authorDetailResponse.setRole((String) authorMap.get("role"));
        authorDetailResponse.setActive((Boolean) authorMap.get("is_active"));
        authorDetailResponse.setCreatedAt((String) authorMap.get("created_at"));
        authorDetailResponse.setUpdatedAt((String) authorMap.get("updated_at"));

        return ResponseUtility.createResultDTO("Succes get detail author", authorDetailResponse);
    }

    @Override
    @Transactional
    public void updateAuthor(UUID id, AuthorUpdateRequestDTO dto) {
        AuthorEntity author = authorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("invalid.authorId"));
        UserEntity user = userRepository.findByIdUserIdAuthor(author.getUserId().getId());
        user.setEmail(dto.getEmail() == null ? user.getEmail() : dto.getEmail());
        user.setUsername(dto.getUsername() == null ? user.getUsername() : dto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()) == null ? user.getPassword() : bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setUpdatedAt(Timestamp.valueOf(java.time.LocalDateTime.now()));
        author.setName(dto.getName() == null ? author.getName() : dto.getName());

        userRepository.save(user);
        authorRepository.save(author);
    }
}
