package com.review.paper_review.presentation.controller;

import com.review.paper_review.application.service.UserService;
import com.review.paper_review.presentation.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String readUsers(Model model) {
        List<UserDto> userDtos = userService.getAllUsers();
        model.addAttribute("users", userDtos);
        return "users-read";
    }

}
