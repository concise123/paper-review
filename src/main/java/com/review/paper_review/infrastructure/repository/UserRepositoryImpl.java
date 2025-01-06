package com.review.paper_review.infrastructure.repository;

import com.review.paper_review.domain.user.Role;
import com.review.paper_review.domain.user.User;
import com.review.paper_review.domain.user.UserPK;
import com.review.paper_review.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 회원 가입
    @Override
    public int save(User user) {
        String sql = "INSERT INTO users (email, registration_id, role) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, user.getEmail(), user.getRegistrationId(), user.getRole().name());
    }

    // 회원 정보 전체 조회
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    // 이메일 주소와 등록 아이디로 회원 정보 조회
    @Override
    public Optional<User> findById(UserPK id) {
        String sql = "SELECT * FROM users WHERE email = ? AND registration_id = ?";
        return jdbcTemplate.query(sql, new Object[]{id.getEmail(), id.getRegistrationId()}, new UserRowMapper()).stream().findFirst();
    }

    // 권한 수정
    @Override
    public int updateRole(User user) {
        String sql = "UPDATE users SET role = ? WHERE email = ? AND registration_id = ?";
        return jdbcTemplate.update(sql, user.getRole().name(), user.getEmail(), user.getRegistrationId());
    }

    // 회원 탈퇴
    @Override
    public int delete(UserPK id) {
        String sql = "DELETE FROM users WHERE email = ? AND registration_id = ?";
        return jdbcTemplate.update(sql, id.getEmail(), id.getRegistrationId());
    }

    // 회원 정보를 매핑하는 RowMapper 클래스
    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setEmail(rs.getString("email"));
            user.setRegistrationId(rs.getString("registration_id"));
            user.setRole(Role.valueOf(rs.getString("role")));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return user;
        }
    }

}
