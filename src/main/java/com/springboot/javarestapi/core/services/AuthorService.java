package com.springboot.javarestapi.core.services;

import com.springboot.javarestapi.core.domain.dto.AuthorCreateRequestDTO;
import com.springboot.javarestapi.core.domain.dto.AuthorListResponse;
import com.springboot.javarestapi.core.domain.dto.ResponseData;
import com.springboot.javarestapi.metadata.Metadata;

import java.util.List;

public interface AuthorService {

    void createNewAuthor(AuthorCreateRequestDTO payload);

    ResponseData.WithMeta<List<AuthorListResponse>> getListAuthor(Metadata meta);
}
