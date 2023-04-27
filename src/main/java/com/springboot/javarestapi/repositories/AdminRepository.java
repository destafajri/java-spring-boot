package com.springboot.javarestapi.repositories;

import com.springboot.javarestapi.core.domain.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {
    @Modifying
    @Query(value = "SELECT admins.id, admins.user_id, users.username, admins.name, users.is_active, users.created_at, users.updated_at" +
            " FROM admins" +
            " JOIN users ON" +
            " admins.user_id = users.id" +
            " ORDER BY :sort" +
            " LIMIT :limit" +
            " OFFSET :page", nativeQuery = true)
    List<List<String>> getAdminList(
            @Param("sort") Object sort,
            @Param("page") Integer offset,
            @Param("limit") Integer limit);
}
