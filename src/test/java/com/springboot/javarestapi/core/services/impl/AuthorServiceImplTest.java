package com.springboot.javarestapi.core.services.impl;

import com.springboot.javarestapi.core.domain.dto.*;
import com.springboot.javarestapi.core.domain.entities.AuthorEntity;
import com.springboot.javarestapi.core.domain.entities.UserEntity;
import com.springboot.javarestapi.exception.NotFoundException;
import com.springboot.javarestapi.metadata.Metadata;
import com.springboot.javarestapi.repositories.AuthorRepository;
import com.springboot.javarestapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewAuthor_shouldSaveUserAndAuthor() {
        // Arrange
        AuthorCreateRequestDTO payload = new AuthorCreateRequestDTO();
        payload.setEmail("test@example.com");
        payload.setUsername("testuser");
        payload.setPassword("password");
        payload.setName("John Doe");

        UserEntity savedUser = new UserEntity();
        savedUser.setId(UUID.randomUUID());
        savedUser.setEmail(payload.getEmail());
        savedUser.setUsername(payload.getUsername());
        savedUser.setPassword("hashedPassword");
        savedUser.setRole("author");
        savedUser.setActive(true);
        savedUser.setCreatedAt(Timestamp.valueOf(java.time.LocalDateTime.now()));

        AuthorEntity savedAuthor = new AuthorEntity();
        savedAuthor.setId(UUID.randomUUID());
        savedAuthor.setUserId(savedUser);
        savedAuthor.setName(payload.getName());

        when(bCryptPasswordEncoder.encode(payload.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(savedAuthor);

        // Act
        authorService.createNewAuthor(payload);

        // Assert
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(authorRepository, times(1)).save(any(AuthorEntity.class));
    }

    @Test
    void getListAuthor_shouldReturnListOfAuthors() {
        // Arrange
        Metadata meta = new Metadata();
        meta.setPage(1);
        meta.setPerPage(10);
        meta.setOrderBy("asc");
        meta.setSortBy("name");

        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "name"));
        Pageable pageable = PageRequest.of(meta.getPageForQuery(), meta.getPerPage(), sort);

        List<LinkedHashMap<Object, Object>> authors = Arrays.asList(
                createAuthorMap("1", "user1", "user1@example.com", "John Doe"),
                createAuthorMap("2", "user2", "user2@example.com", "Jane Smith")
        );

        when(authorRepository.getListAuthorNativeQuery(pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize()))
                .thenReturn(authors);

        // Act
        ResponseData.WithMeta<List<AuthorListResponse>> response = authorService.getListAuthor(meta);

        // Assert
        verify
                (authorRepository, times(1)).getListAuthorNativeQuery(pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize());
        assertEquals(2, response.getData().size());
        assertTrue(response.getData().containsAll(authors));
        assertEquals("Success get list author", response.getMessage().get(0));
    }

    @Test
    void getDetailAuthor_existingId_shouldReturnAuthorDetailResponse() {
        // Arrange
        UUID authorId = UUID.randomUUID();
        LinkedHashMap<Object, Object> authorMap = createAuthorMap(authorId.toString(), "user1", "user1@example.com", "John Doe");

        when(authorRepository.getDetailAuthorNativeQuery(authorId)).thenReturn(authorMap);

        // Act
        ResponseData<AuthorDetailResponse> response = authorService.getDetailAuthor(authorId);

        // Assert
        verify(authorRepository, times(1)).getDetailAuthorNativeQuery(authorId);
        assertEquals(authorId, response.getData().getId());
        assertEquals("user1", response.getData().getUsername());
        assertEquals("user1@example.com", response.getData().getEmail());
        assertEquals("John Doe", response.getData().getName());
        assertEquals("Succes get detail author", response.getMessage().get(0));
    }

    @Test
    void getDetailAuthor_nonExistingId_shouldThrowNotFoundException() {
        // Arrange
        UUID authorId = UUID.randomUUID();

        when(authorRepository.getDetailAuthorNativeQuery(authorId)).thenReturn(null);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> authorService.getDetailAuthor(authorId));
        verify(authorRepository, times(1)).getDetailAuthorNativeQuery(authorId);
    }

    @Test
    void updateAuthor_existingId_shouldUpdateAuthor() {
        // Arrange
        UUID authorId = UUID.randomUUID();
        AuthorUpdateRequestDTO dto = new AuthorUpdateRequestDTO();
        UserEntity existingUser = new UserEntity();
        AuthorEntity existingAuthor = new AuthorEntity();

        dto.setEmail("newemail@example.com");
        dto.setUsername("newuser");
        dto.setPassword("newpassword");
        dto.setName("Updated Name");

        existingUser.setId(UUID.randomUUID());
        existingUser.setEmail("oldemail@example.com");
        existingUser.setUsername("olduser");
        existingUser.setPassword("oldpassword");
        existingAuthor.setId(authorId);
        existingAuthor.setUserId(existingUser);
        existingAuthor.setName("Old Name");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(userRepository.findById(existingAuthor.getUserId().getId())).thenReturn(Optional.of(existingUser));
        when(bCryptPasswordEncoder.encode(dto.getPassword())).thenReturn("hashedPassword");

        // Act
        authorService.updateAuthor(authorId, dto);

        // Assert
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(authorRepository, times(1)).save(any(AuthorEntity.class));
        assertEquals(dto.getEmail(), existingUser.getEmail());
        assertEquals(dto.getUsername(), existingUser.getUsername());
        assertEquals("hashedPassword", existingUser
                .getPassword());
        assertEquals(dto.getName(), existingAuthor.getName());
    }

    @Test
    void updateAuthor_nonExistingId_shouldThrowNotFoundException() {
        // Arrange
        UUID authorId = UUID.randomUUID();
        AuthorUpdateRequestDTO dto = new AuthorUpdateRequestDTO();

        when(authorRepository.findById(authorId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> authorService.updateAuthor(authorId, dto));
        verify(authorRepository, times(1)).findById(authorId);
    }

    @Test
    void deleteAuthor_existingId_shouldDeleteAuthor() {
        // Arrange
        UUID authorId = UUID.fromString("7e81fb82-ea32-47b1-afd2-e9abe0442da0");
        UUID userID = UUID.fromString("7e81fb82-ea32-47b1-afd2-e9abe0442da0");
        UserEntity existingUser = new UserEntity();
        AuthorEntity existingAuthor = new AuthorEntity();

        existingUser.setId(userID);
        existingUser.setEmail("user@example.com");
        existingUser.setUsername("user");
        existingUser.setPassword("password");
        existingAuthor.setId(authorId);
        existingAuthor.setUserId(existingUser);
        existingAuthor.setName("John Doe");


        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(userRepository.findById(userID)).thenReturn(Optional.of(existingUser));

        // Act
        authorService.deleteAuthor(authorId);

        // Assert
        verify(authorRepository, times(1)).delete(existingAuthor);
        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    void deleteAuthor_nonExistingId_shouldThrowNotFoundException() {
        // Arrange
        UUID authorId = UUID.randomUUID();

        when(authorRepository.findById(authorId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> authorService.deleteAuthor(authorId));
        verify(authorRepository, times(1)).findById(authorId);
    }

    // Helper method to create a LinkedHashMap for author data
    private LinkedHashMap<Object, Object> createAuthorMap(String id, String username, String email, String name) {
        LinkedHashMap<Object, Object> authorMap = new LinkedHashMap<>();
        authorMap.put("id", id);
        authorMap.put("user_id", UUID.randomUUID().toString());
        authorMap.put("username", username);
        authorMap.put("email", email);
        authorMap.put("name", name);
        authorMap.put("role", "author");
        authorMap.put("is_active", true);
        authorMap.put("created_at", "2023-05-24 12:34:56");
        authorMap.put("updated_at", "2023-05-24 12:34:56");
        return authorMap;
    }
}