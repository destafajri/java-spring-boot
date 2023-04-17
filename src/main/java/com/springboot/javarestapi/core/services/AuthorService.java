package com.springboot.javarestapi.core.services;

import com.springboot.javarestapi.core.domain.dto.AuthorCreateRequestDTO;
import com.springboot.javarestapi.core.domain.dto.AuthorDetailResponse;
import com.springboot.javarestapi.core.domain.dto.AuthorListResponse;
import com.springboot.javarestapi.core.domain.dto.ResponseData;
import com.springboot.javarestapi.metadata.Metadata;

import java.util.List;
import java.util.UUID;

public interface AuthorService {

    void createNewAuthor(AuthorCreateRequestDTO payload);

    ResponseData.WithMeta<List<AuthorListResponse>> getListAuthor(Metadata meta);

    ResponseData<AuthorDetailResponse> getDetailAuthor(UUID id);
}
