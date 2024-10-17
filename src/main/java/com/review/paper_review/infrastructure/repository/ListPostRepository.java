package com.review.paper_review.infrastructure.repository;

import com.review.paper_review.domain.post.Post;
import com.review.paper_review.domain.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ListPostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ListPostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 게시글 작성
    @Override
    public int save(Post post) {
        String sql = "INSERT INTO posts (title, content) VALUES (?, ?)";
        return jdbcTemplate.update(sql, post.getTitle(), post.getContent());
    }

    // 게시글 전체 조회
    @Override
    public List<Post> findAll() {
        String sql = "SELECT * FROM posts";
        return jdbcTemplate.query(sql, new PostRowMapper());
    }

    // 아이디로 게시글 조회
    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new PostRowMapper()).stream().findFirst();
    }

    // 게시글 수정
    public int update(Post post) {
        String sql = "UPDATE posts SET title = ?, content = ? WHERE id = ?";
        return jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getId());
    }

    // 게시글 삭제
    public int delete(Long id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // 게시글을 매핑하는 RowMapper 클래스
    public static class PostRowMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return post;
        }
    }

}
