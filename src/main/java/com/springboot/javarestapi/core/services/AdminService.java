package com.springboot.javarestapi.core.services;

import com.springboot.javarestapi.core.domain.dto.AdminCreateRequestDTO;
import com.springboot.javarestapi.core.domain.dto.AdminListResponse;
import com.springboot.javarestapi.core.domain.dto.ResponseData;
import com.springboot.javarestapi.metadata.Metadata;

import java.util.List;

public interface AdminService {
    void createNewAdmin(AdminCreateRequestDTO dto);

    ResponseData.WithMeta<List<AdminListResponse>> getListAdmin(Metadata meta);
}
