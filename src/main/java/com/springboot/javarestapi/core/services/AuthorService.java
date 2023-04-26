package com.springboot.javarestapi.core.services;

import com.springboot.javarestapi.core.domain.dto.*;
import com.springboot.javarestapi.metadata.Metadata;

import java.util.List;
import java.util.UUID;

public interface AuthorService {

    void createNewAuthor(AuthorCreateRequestDTO payload);

    ResponseData.WithMeta<List<AuthorListResponse>> getListAuthor(Metadata meta);

    ResponseData<AuthorDetailResponse> getDetailAuthor(UUID id);

    void updateAuthor(UUID id, AuthorUpdateRequestDTO dto);
}
