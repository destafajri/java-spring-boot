package com.springboot.javarestapi.repositories;

import com.springboot.javarestapi.core.domain.entities.AuthorEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {

    @Query(value = "SELECT json_build_object(" +
            " 'id', authors.id," +
            " 'user_id', user_id," +
            " 'username', username," +
            " 'name', name," +
            " 'is_active', is_active," +
            " 'created_at', created_at," +
            " 'updated_at', updated_at" +
            ")" +
            " FROM authors JOIN users ON" +
            " authors.user_id = users.id" +
            " ORDER BY :sort" +
            " LIMIT :limit" +
            " OFFSET :page", nativeQuery = true)
    List<LinkedHashMap<String, Object>> getListAuthorNativeQuery(
            @Param("page") Integer offset,
            @Param("limit") Integer limit,
            @Param("sort") String sort);

    @Query(value = "SELECT COUNT(*) FROM authors", nativeQuery = true)
    Integer totalAuthor();
}