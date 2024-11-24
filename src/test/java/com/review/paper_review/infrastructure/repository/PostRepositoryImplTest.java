package com.review.paper_review.infrastructure.repository;

import com.review.paper_review.domain.post.Post;
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

public class PostRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PostRepositoryImpl postRepository;

    private Post mockPost;
    private Post mockPost2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockPost = Post.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .build();
        mockPost2 = Post.builder()
                .id(2L)
                .title("제목 2")
                .content("내용 2")
                .build();
    }

    @Test
    @DisplayName("게시글 작성")
    void testSave() {
        postRepository.save(mockPost);

        // 결과 검증
        verify(jdbcTemplate).update("INSERT INTO posts (title, content) VALUES (?, ?)", mockPost.getTitle(), mockPost.getContent());
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void testFindAll() {
        // jdbcTemplate.query 메서드가 호출되었을 때 가짜 데이터를 반환하도록 설정
        when(jdbcTemplate.query(any(String.class), any(PostRepositoryImpl.PostRowMapper.class)))
                .thenReturn(Arrays.asList(mockPost, mockPost2));

        List<Post> result = postRepository.findAll();

        // 결과 검증
        assertEquals(2, result.size());
        assertEquals("제목", result.get(0).getTitle());
        assertEquals("내용", result.get(0).getContent());
        assertEquals("제목 2", result.get(1).getTitle());
        assertEquals("내용 2", result.get(1).getContent());
    }

    @Test
    @DisplayName("아이디로 게시글 조회")
    void testFindById_Found() {
        // jdbcTemplate.query 메서드가 호출되었을 때 가짜 데이터를 반환하도록 설정
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(PostRepositoryImpl.PostRowMapper.class)))
                .thenReturn(Collections.singletonList(mockPost));

        Optional<Post> foundPost = postRepository.findById(1L);

        // 결과 검증
        assertTrue(foundPost.isPresent());
        assertEquals("제목", foundPost.get().getTitle());
    }

    @Test
    @DisplayName("아이디로 게시글 조회 - 해당하는 게시글 못 찾음")
    void testFindById_NotFound() {
        // jdbcTemplate.query가 호출되었을 때 결과가 없도록 설정
        when(jdbcTemplate.query(any(String.class), any(Object[].class), any(PostRepositoryImpl.PostRowMapper.class)))
                .thenReturn(Collections.emptyList());

        Optional<Post> result = postRepository.findById(-1L);

        // 결과 검증
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdate() {
        // 업데이트가 성공었다고 가정
        when(jdbcTemplate.update(any(String.class), any(), any(), any())).thenReturn(1);

        int result = postRepository.update(mockPost);

        // 결과 검증
        verify(jdbcTemplate).update(
                "UPDATE posts SET title = ?, content = ? WHERE id = ?",
                mockPost.getTitle(),
                mockPost.getContent(),
                mockPost.getId()
        );
        assertEquals(1, result);
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDelete() {
        Long postId = 1L;

        postRepository.delete(postId);

        // 결과 검증
        verify(jdbcTemplate).update("DELETE FROM posts WHERE id = ?", postId);
    }

}
