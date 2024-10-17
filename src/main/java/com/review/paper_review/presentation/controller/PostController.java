package com.review.paper_review.presentation.controller;

import com.review.paper_review.application.service.PostService;
import com.review.paper_review.presentation.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostController {

    private PostService postService;

    @Autowired
    PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "index";
    }

    @GetMapping("/posts/create")
    public String createPost() {
        return "posts-create";
    }

    @GetMapping("/posts/update/{id}")
    public String updatePost(@PathVariable Long id, Model model) {
        PostDto dto = postService.getPostById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

}
