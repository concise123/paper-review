package com.review.paper_review.application.service;

import com.review.paper_review.application.mapper.UserMapper;
import com.review.paper_review.domain.exception.UserNotFoundException;
import com.review.paper_review.domain.user.Role;
import com.review.paper_review.domain.user.User;
import com.review.paper_review.domain.user.UserPK;
import com.review.paper_review.domain.user.UserRepository;
import com.review.paper_review.presentation.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public int updateUserRole(String email, String registrationId, String role) {
        UserPK id = new UserPK(email, registrationId);
        User user = findById(id);
        user.setRole(Role.valueOf(role));
        return userRepository.updateRole(user);
    }

    public int deleteUser(String email, String registrationId) {
        UserPK id = new UserPK(email, registrationId);
        User user = findById(id);
        return userRepository.delete(id);
    }

    public User findById(UserPK id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없습니다."));
    }

}
