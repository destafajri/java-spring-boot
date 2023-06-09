package com.springboot.javarestapi.repositories;

import com.springboot.javarestapi.core.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {

    @Modifying
    @Query(value = "SELECT json_build_object(" +
            " 'id', authors.id," +
            " 'user_id', user_id," +
            " 'username', username," +
            " 'name', name," +
            " 'is_active', is_active," +
            " 'created_at', created_at," +
            " 'updated_at', updated_at" +
            ")" +
            " FROM authors" +
            " JOIN users ON" +
            " authors.user_id = users.id" +
            " ORDER BY :sort" +
            " LIMIT :limit" +
            " OFFSET :page", nativeQuery = true)
    List<LinkedHashMap<Object, Object>> getListAuthorNativeQuery(
            @Param("sort") Object sort,
            @Param("page") Integer offset,
            @Param("limit") Integer limit);

    @Query(value = "SELECT COUNT(*) FROM authors", nativeQuery = true)
    Integer totalAuthor();

    @Query(value = "SELECT json_build_object(" +
            " 'id', authors.id," +
            " 'user_id', user_id," +
            " 'username', username," +
            " 'email', email," +
            " 'name', name," +
            " 'role', role," +
            " 'is_active', is_active," +
            " 'created_at', created_at," +
            " 'updated_at', updated_at" +
            ")" +
            " FROM authors JOIN users ON" +
            " authors.user_id = users.id" +
            " WHERE authors.id = :id", nativeQuery = true)
    LinkedHashMap<Object, Object> getDetailAuthorNativeQuery(@Param("id") UUID id);
}