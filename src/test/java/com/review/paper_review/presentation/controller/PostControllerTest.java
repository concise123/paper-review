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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    @DisplayName("수정 화면 요청")
    public void testUpdatePost() throws Exception {
        Long postId = 1L;
        PostDto mockPostDto = PostDto.builder()
                .id(postId)
                .title("제목")
                .content("내용")
                .build();

        when(postService.getPostById(postId)).thenReturn(mockPostDto);

        mockMvc.perform(get("/posts/update/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(view().name("posts-update"))
                .andExpect(model().attributeExists("post"));
    }

}
