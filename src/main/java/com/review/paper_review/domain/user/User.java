package com.review.paper_review.domain.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private UserPK id;
    private String email;
    private String registrationId;
    private Role role;
    private LocalDateTime createdAt;

    @Builder
    public User(UserPK id, String email, String registrationId, Role role, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.registrationId = registrationId;
        this.role = role;
        this.createdAt = createdAt;
    }

}
