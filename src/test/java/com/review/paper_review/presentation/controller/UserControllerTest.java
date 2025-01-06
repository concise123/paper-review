package com.review.paper_review.presentation.controller;

import com.review.paper_review.application.service.UserService;
import com.review.paper_review.domain.user.Role;
import com.review.paper_review.presentation.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @WithMockUser
    @Test
    @DisplayName("회원 정보 조회 화면 요청")
    public void testReadUsers() throws Exception {
        List<UserDto> mockPostDtos = new ArrayList<>();
        UserDto mockUserDto = UserDto.builder()
                .email("test@gmail.com")
                .registrationId("google")
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();
        UserDto mockUserDto2 = UserDto.builder()
                .email("test2@gmail.com")
                .registrationId("google")
                .role(Role.ADMIN)
                .createdAt(LocalDateTime.now())
                .build();
        mockPostDtos.add(mockUserDto);
        mockPostDtos.add(mockUserDto2);

        when(userService.getAllUsers()).thenReturn(mockPostDtos);

        mockMvc.perform(get("/users").with(oauth2Login()))
                .andExpect(status().isOk())
                .andExpect(view().name("users-read"))
                .andExpect(model().attribute("users", mockPostDtos));
    }

}
