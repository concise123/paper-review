package com.review.paper_review.presentation.controller;

import com.review.paper_review.application.service.PostService;
import com.review.paper_review.presentation.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private final PostService postService;

    @Autowired
    PostRestController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성
    @PostMapping
    public Integer createPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    // 게시글 전체 조회
    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    // 아이디로 게시글 조회
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public Integer updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        return postService.updatePost(id, postDto);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public Integer deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

}
