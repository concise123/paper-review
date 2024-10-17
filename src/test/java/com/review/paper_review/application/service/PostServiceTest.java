package com.review.paper_review.application.service;

import com.review.paper_review.domain.exception.PostNotFoundException;
import com.review.paper_review.domain.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("PostService가 제대로 주입되었는지 확인")
    public void postServiceShouldBeNotNull() {
        assertThat(postService).isNotNull();
    }

    @Test
    @DisplayName("아이디로 게시글 조회 - 존재하지 않는 id로 조회하면 PostNotFoundException이 발생해야 한다.")
    public void shouldThrowPostNotFoundException_WhenPostIdNotFound() {
        Long postId = -1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> {
            postService.findById(postId);
        });
    }

}
