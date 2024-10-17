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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .build();

        when(postService.getPostById(postId)).thenReturn(mockPostDto);

        mockMvc.perform(get("/api/v1/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"));
    }

}
