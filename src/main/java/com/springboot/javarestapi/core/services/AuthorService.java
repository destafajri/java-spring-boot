package com.springboot.javarestapi.core.services;

import com.springboot.javarestapi.core.domain.dto.AuthorCreateRequestDTO;
import com.springboot.javarestapi.core.domain.dto.AuthorListResponse;

import java.util.List;

public interface AuthorService {

    public void createNewAuthor(AuthorCreateRequestDTO payload);
    public List<AuthorListResponse> getListAuthor();
}
