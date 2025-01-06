package com.review.paper_review.presentation.controller;

import com.review.paper_review.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // 권한 수정
    @PutMapping("/{email}/{registrationId}/{role}")
    public Integer updateUserRole(@PathVariable String email, @PathVariable String registrationId, @PathVariable String role) {
        return userService.updateUserRole(email, registrationId, role);
    }

    // 회원 탈퇴
    @DeleteMapping("/{email}/{registrationId}")
    public Integer deleteUser(@PathVariable String email, @PathVariable String registrationId) {
        return userService.deleteUser(email, registrationId);
    }

}
