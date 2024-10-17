package com.review.paper_review.application.service;

import com.review.paper_review.application.mapper.PostMapper;
import com.review.paper_review.domain.exception.PostNotFoundException;
import com.review.paper_review.domain.post.Post;
import com.review.paper_review.domain.post.PostRepository;
import com.review.paper_review.presentation.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Autowired
    PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public int createPost(PostDto postDto) {
        Post post = postMapper.toEntity(postDto);
        return postRepository.save(post);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .toList();
    }

    public PostDto getPostById(Long id) {
        Post post = findById(id);
        return postMapper.toDto(post);
    }

    public int updatePost(Long id, PostDto postDto) {
        Post post = findById(id);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return postRepository.update(post);
    }

    public int deletePost(Long id) {
        Post post = findById(id);
        return postRepository.delete(post.getId());
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("글번호 " + id + "에 해당하는 게시글을 찾을 수 없습니다."));
    }

}

