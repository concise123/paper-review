package com.review.paper_review.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String writerId;

    public PostDto(String title, String content, String writerId) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }

    @Builder
    public PostDto(Long id, String title, String content, LocalDateTime createdAt, String writerId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.writerId = writerId;
    }

}
