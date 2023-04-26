package com.springboot.javarestapi.core.services.impl;

import com.springboot.javarestapi.core.domain.dto.AdminCreateRequestDTO;
import com.springboot.javarestapi.core.domain.entities.AdminEntity;
import com.springboot.javarestapi.core.domain.entities.UserEntity;
import com.springboot.javarestapi.core.services.AdminService;
import com.springboot.javarestapi.repositories.AdminRepository;
import com.springboot.javarestapi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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
}
