package com.review.paper_review.infrastructure.repository;

import com.review.paper_review.domain.user.Role;
import com.review.paper_review.domain.user.User;
import com.review.paper_review.domain.user.UserPK;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    private User mockUser;
    private User mockUser2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = User.builder()
                .email("test@gmail.com")
                .registrationId("google")
                .role(Role.USER)
                .build();
        mockUser2 = User.builder()
                .email("test2@gmail.com")
                .registrationId("google")
                .role(Role.ADMIN)
                .build();
    }

    @Test
    @DisplayName("회원 가입")
    void testSave() {
        userRepository.save(mockUser);
        // 결과 검증
        verify(jdbcTemplate).update("INSERT INTO users (email, registration_id, role) VALUES (?, ?, ?)", mockUser.getEmail(), mockUser.getRegistrationId(), mockUser.getRole().name());
    }

    @Test
    @DisplayName("회원 정보 전체 조회")
    void testFindAll() {
        // jdbcTemplate.query 메서드가 호출되었을 때 가짜 데이터를 반환하도록 설정
        when(jdbcTemplate.query(any(String.class), any(UserRepositoryImpl.UserRowMapper.class)))
                .thenReturn(Arrays.asList(mockUser, mockUser2));

        List<User> result = userRepository.findAll();

        // 결과 검증
        assertEquals(2, result.size());
        assertEquals("test@gmail.com", result.get(0).getEmail());
        assertEquals("google", result.get(0).getRegistrationId());
        assertEquals(Role.USER, result.get(0).getRole());
        assertEquals("test2@gmail.com", result.get(1).getEmail());
        assertEquals("google", result.get(1).getRegistrationId());
        assertEquals(Role.ADMIN, result.get(1).getRole());
    }

    @Test
    @DisplayName("이메일 주소와 등록 아이디로 회원 정보 조회")
    void testFindById_Found() {
        // jdbcTemplate.query 메서드가 호출되었을 때 가짜 데이터를 반환하도록 설정
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(UserRepositoryImpl.UserRowMapper.class)))
                .thenReturn(Collections.singletonList(mockUser));

        Optional<User> foundUser = userRepository.findById(new UserPK("test@gmail.com", "google"));

        // 결과 검증
        assertTrue(foundUser.isPresent());
        assertEquals(Role.USER, foundUser.get().getRole());
    }

    @Test
    @DisplayName("이메일 주소와 등록 아이디로 회원 정보 조회 - 해당하는 회원 못 찾음")
    void testFindById_NotFound() {
        // jdbcTemplate.query가 호출되었을 때 결과가 없도록 설정
        when(jdbcTemplate.query(any(String.class), any(Object[].class), any(UserRepositoryImpl.UserRowMapper.class)))
                .thenReturn(Collections.emptyList());

        Optional<User> result = userRepository.findById(new UserPK("test@gmail.com", "google"));

        // 결과 검증
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("권한 수정")
    void testUpdateRole() {
        // 업데이트가 성공었다고 가정
        when(jdbcTemplate.update(any(String.class), any(), any(), any())).thenReturn(1);

        int result = userRepository.updateRole(mockUser);

        // 결과 검증
        verify(jdbcTemplate).update(
                "UPDATE users SET role = ? WHERE email = ? AND registration_id = ?",
                mockUser.getRole().name(),
                mockUser.getEmail(),
                mockUser.getRegistrationId()
        );
        assertEquals(1, result);
    }

    @Test
    @DisplayName("회원 탈퇴")
    void testDelete() {
        UserPK id = new UserPK(mockUser.getEmail(), mockUser.getRegistrationId());

        userRepository.delete(id);

        // 결과 검증
        verify(jdbcTemplate).update("DELETE FROM users WHERE email = ? AND registration_id = ?", id.getEmail(), id.getRegistrationId());
    }

}
