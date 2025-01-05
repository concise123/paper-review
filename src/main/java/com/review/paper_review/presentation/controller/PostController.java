package com.review.paper_review.presentation.controller;

import com.review.paper_review.application.service.PostService;
import com.review.paper_review.config.SecurityUtil;
import com.review.paper_review.presentation.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String index(Model model) {
        String userId = SecurityUtil.getCurrentUserId();
        if (userId != null) {
            model.addAttribute("userId", userId);
        }
        model.addAttribute("posts", postService.getAllPosts());
        return "index";
    }

    @GetMapping("/posts/create")
    public String createPost(Model model) {
        model.addAttribute("writerId", SecurityUtil.getCurrentUserId());
        return "posts-create";
    }

    @GetMapping("/posts/update/{id}")
    public String updatePost(@PathVariable Long id, Model model) {
        // 아이디로 게시글 조회
        PostDto dto = postService.getPostById(id);
        // 로그인한 사용자와 게시글 작성자가 일치하는지 확인
        String userId = SecurityUtil.getCurrentUserId();
        boolean editable = userId!= null && userId.equals(dto.getWriterId());
        model.addAttribute("post", dto);
        model.addAttribute("editable", editable);
        return "posts-update";
    }

}
