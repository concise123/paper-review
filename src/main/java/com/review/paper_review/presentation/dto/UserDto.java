package com.review.paper_review.presentation.dto;

import com.review.paper_review.domain.user.Role;
import com.review.paper_review.domain.user.UserPK;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private UserPK id;
    private String email;
    private String registrationId;
    private Role role;
    private LocalDateTime createdAt;

    @Builder
    public UserDto(UserPK id, String email, String registrationId, Role role, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.registrationId = registrationId;
        this.role = role;
        this.createdAt = createdAt;
    }

}
