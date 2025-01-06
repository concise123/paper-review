package com.review.paper_review.application.mapper;

import com.review.paper_review.domain.user.User;
import com.review.paper_review.presentation.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(UserDto userDto);

}
