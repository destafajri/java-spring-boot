package com.springboot.javarestapi.controller;

import com.springboot.javarestapi.core.domain.dto.AuthorCreateRequestDTO;
import com.springboot.javarestapi.core.domain.dto.AuthorListResponse;
import com.springboot.javarestapi.core.services.AuthorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<Void> authorCreate(@RequestBody @Valid AuthorCreateRequestDTO dto) {
        authorService.createNewAuthor(dto);
        return ResponseEntity.created(URI.create("/author/create")).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<AuthorListResponse>> authorList() {
        return ResponseEntity.ok().body(authorService.getListAuthor());
    }
}
