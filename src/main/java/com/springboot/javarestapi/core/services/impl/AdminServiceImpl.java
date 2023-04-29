package com.springboot.javarestapi.core.services.impl;

import com.springboot.javarestapi.core.domain.dto.AdminCreateRequestDTO;
import com.springboot.javarestapi.core.domain.dto.AdminListResponse;
import com.springboot.javarestapi.core.domain.dto.ResponseData;
import com.springboot.javarestapi.core.domain.dto.ResponseUtility;
import com.springboot.javarestapi.core.domain.entities.AdminEntity;
import com.springboot.javarestapi.core.domain.entities.UserEntity;
import com.springboot.javarestapi.core.services.AdminService;
import com.springboot.javarestapi.metadata.Metadata;
import com.springboot.javarestapi.metadata.PagintationUtility;
import com.springboot.javarestapi.repositories.AdminRepository;
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
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Override
    @Transactional
    public void createNewAdmin(AdminCreateRequestDTO payload) {
        UserEntity user = new UserEntity();
        AdminEntity admin = new AdminEntity();

        user.setEmail(payload.getEmail());
        user.setUsername(payload.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(payload.getPassword()));
        user.setRole("admin");
        user.setActive(true);
        user.setCreatedAt(Timestamp.valueOf(java.time.LocalDateTime.now()));
        admin.setUserId(user);
        admin.setName(payload.getName());

        userRepository.save(user);
        adminRepository.save(admin);
    }

    @Override
    public ResponseData.WithMeta<List<AdminListResponse>> getListAdmin(Metadata meta) {
        Sort sort = Sort.by(new Sort.Order(PagintationUtility.getOrderBy(meta.getOrderBy()), meta.getSortBy()));
        Pageable pageable = PageRequest.of(meta.getPageForQuery(), meta.getPerPage(), sort);

        /**
         * sorting doesn't work
         *
         * And return value still got some bug
         */
//        List<AdminListResponse> adminResp = adminRepository.getAdminList(pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize());

        meta.setTotal((int) adminRepository.count());
        return ResponseUtility.createResultWithMetaDTO(meta.getPage(), meta.getPerPage(), meta.getTotal(), meta.getSortBy(), meta.getOrderBy(), "Success get list admin", null);
    }
}
