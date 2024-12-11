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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
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
    @DisplayName("로그인한 사용자와 게시글 작성자가 일치할 때 수정 화면 요청")
    public void testUpdatePostWhenUserIsWriter() throws Exception {
        Long postId = 1L;
        PostDto mockPostDto = PostDto.builder()
                .id(postId)
                .title("제목")
                .content("내용")
                .writerId("test@gmail.com")
                .build();

        when(postService.getPostById(postId)).thenReturn(mockPostDto);

        mockMvc.perform(get("/posts/update/{id}", postId).with(oauth2Login()
                        .attributes(attributes -> {
                            attributes.put("email", "test@gmail.com");
                        })))
                .andExpect(status().isOk())
                .andExpect(view().name("posts-update"))
                .andExpect(model().attribute("post", mockPostDto))
                .andExpect(model().attribute("editable", true));
    }

    @Test
    @DisplayName("로그인한 사용자와 게시글 작성자가 일치하지 않을 때 수정 화면 요청")
    public void testUpdatePostWhenUserIsNotWriter() throws Exception {
        Long postId = 1L;
        PostDto mockPostDto = PostDto.builder()
                .id(postId)
                .title("제목")
                .content("내용")
                .writerId("test@gmail.com")
                .build();

        when(postService.getPostById(postId)).thenReturn(mockPostDto);

        mockMvc.perform(get("/posts/update/{id}", postId).with(oauth2Login()
                        .attributes(attributes -> {
                            attributes.put("email", "test2@gmail.com");
                        })))
                .andExpect(status().isOk())
                .andExpect(view().name("posts-update"))
                .andExpect(model().attribute("post", mockPostDto))
                .andExpect(model().attribute("editable", false));
    }

}
