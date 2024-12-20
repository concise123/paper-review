package com.review.paper_review.domain.post;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Post {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String writerId;

    @Builder
    public Post(Long id, String title, String content, LocalDateTime createdAt, String writerId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.writerId = writerId;
    }

}
