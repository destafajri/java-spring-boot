package com.springboot.javarestapi.core.services;

import com.springboot.javarestapi.core.domain.dto.AdminCreateRequestDTO;

public interface AdminService {
    void createNewAdmin(AdminCreateRequestDTO dto);
}
