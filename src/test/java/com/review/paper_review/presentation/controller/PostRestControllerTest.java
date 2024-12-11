package com.review.paper_review.presentation.controller;

import com.review.paper_review.application.service.PostService;
import com.review.paper_review.presentation.dto.PostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostRestController.class)
public class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @InjectMocks
    private PostRestController postRestController;

    @Test
    @DisplayName("아이디로 게시글 조회")
    public void testGetPostById_ReturnsPost() throws Exception {
        Long postId = 1L;
        PostDto mockPostDto = PostDto.builder()
                .id(postId)
                .title("제목")
                .content("내용")
                .writerId("작성자")
                .build();

        when(postService.getPostById(postId)).thenReturn(mockPostDto);

        mockMvc.perform(get("/api/v1/posts/{id}", postId).with(oauth2Login())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andExpect(jsonPath("$.writerId").value("작성자"));
    }

    @Test
    @DisplayName("아이디로 게시글 조회 - 미로그인 상태일 때 OAuth2 로그인 페이지로 리디렉션되는지 확인")
    public void testOAuth2LoginRedirectWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/oauth2/authorization/google"));
    }

}
