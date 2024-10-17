package com.review.paper_review.application.mapper;

import com.review.paper_review.domain.post.Post;
import com.review.paper_review.presentation.dto.PostDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper { // DTO와 Entity 간 변환을 위한 인터페이스

    PostDto toDto(Post post);
    Post toEntity(PostDto postDto);

}
