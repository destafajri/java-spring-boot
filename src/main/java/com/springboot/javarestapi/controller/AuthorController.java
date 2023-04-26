package com.springboot.javarestapi.controller;

import com.springboot.javarestapi.core.domain.dto.*;
import com.springboot.javarestapi.core.services.AuthorService;
import com.springboot.javarestapi.metadata.Metadata;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<ResponseData<String>> authorCreate(
            @RequestBody @Valid AuthorCreateRequestDTO dto, Errors errors) {
        ResponseData responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }

            responseData.setCode(HttpStatus.BAD_REQUEST.value());
            responseData.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        authorService.createNewAuthor(dto);

        responseData.setCode(HttpStatus.CREATED.value());
        responseData.setStatus(HttpStatus.CREATED);
        responseData.setMessage(Collections.singletonList("Success Create New Author"));
        return ResponseEntity.created(URI.create("/author/create")).body(responseData);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseData.WithMeta<List<AuthorListResponse>>> authorList(
            @RequestParam(name = "page", defaultValue = "1") Integer pages,
            @RequestParam(name = "per_page", defaultValue = "10") Integer perPages,
            @RequestParam(name = "sort_by", defaultValue = "created_at") String sort,
            @RequestParam(name = "order_by", defaultValue = "desc") String order) {
        Metadata meta = new Metadata();
        meta.setPage(pages);
        meta.setPerPage(perPages);
        meta.setSortBy(sort);
        meta.setOrderBy(order);

        return ResponseEntity.ok().body(authorService.getListAuthor(meta));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseData<AuthorDetailResponse>> authorDetail(
            @PathVariable UUID id) {
        return ResponseEntity.ok().body(authorService.getDetailAuthor(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseData<String>> updateAuthor(
            @PathVariable UUID id,
            @RequestBody @Valid AuthorUpdateRequestDTO dto,
            Errors errors) {
        ResponseData responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }

            responseData.setCode(HttpStatus.BAD_REQUEST.value());
            responseData.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        authorService.updateAuthor(id, dto);

        responseData.setCode(HttpStatus.ACCEPTED.value());
        responseData.setStatus(HttpStatus.ACCEPTED);
        responseData.setMessage(Collections.singletonList("Success Update Author"));
        return ResponseEntity.accepted().body(responseData);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<String>> updateAuthor(
            @PathVariable UUID id) {
        ResponseData responseData = new ResponseData<>();
        authorService.deleteAuthor(id);

        responseData.setCode(HttpStatus.ACCEPTED.value());
        responseData.setStatus(HttpStatus.ACCEPTED);
        responseData.setMessage(Collections.singletonList("Success Delete Author"));
        return ResponseEntity.accepted().body(responseData);
    }
}
