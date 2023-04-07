package com.springboot.javarestapi.core.services;

import com.springboot.javarestapi.core.domain.dto.AuthorCreateRequestDTO;

public interface AuthorService {

    public void createNewAuthor(AuthorCreateRequestDTO payload);
}
