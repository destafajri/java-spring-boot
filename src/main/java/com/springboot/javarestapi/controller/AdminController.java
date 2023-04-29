package com.springboot.javarestapi.controller;

import com.springboot.javarestapi.core.domain.dto.AdminCreateRequestDTO;
import com.springboot.javarestapi.core.domain.dto.AdminListResponse;
import com.springboot.javarestapi.core.domain.dto.ResponseData;
import com.springboot.javarestapi.core.services.AdminService;
import com.springboot.javarestapi.metadata.Metadata;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<ResponseData<String>> adminCreate(
            @RequestBody @Valid AdminCreateRequestDTO dto, Errors errors) {
        ResponseData responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }

            responseData.setCode(HttpStatus.BAD_REQUEST.value());
            responseData.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        adminService.createNewAdmin(dto);

        responseData.setCode(HttpStatus.CREATED.value());
        responseData.setStatus(HttpStatus.CREATED);
        responseData.setMessage(Collections.singletonList("Success Create New Admin"));
        return ResponseEntity.created(URI.create("/admin/create")).body(responseData);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseData.WithMeta<List<AdminListResponse>>> authorList(
            @RequestParam(name = "page", defaultValue = "1") Integer pages,
            @RequestParam(name = "per_page", defaultValue = "10") Integer perPages,
            @RequestParam(name = "sort_by", defaultValue = "created_at") String sort,
            @RequestParam(name = "order_by", defaultValue = "desc") String order) {
        Metadata meta = new Metadata();
        meta.setPage(pages);
        meta.setPerPage(perPages);
        meta.setSortBy(sort);
        meta.setOrderBy(order);

        return ResponseEntity.ok().body(adminService.getListAdmin(meta));
    }
}
