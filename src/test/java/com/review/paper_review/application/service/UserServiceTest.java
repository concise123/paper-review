package com.review.paper_review.application.service;

import com.review.paper_review.domain.exception.UserNotFoundException;
import com.review.paper_review.domain.user.UserPK;
import com.review.paper_review.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("UserService가 제대로 주입되었는지 확인")
    public void userServiceShouldBeNotNull() {
        assertThat(userService).isNotNull();
    }

    @Test
    @DisplayName("이메일 주소와 등록 아이디로 회원 정보 조회 - 존재하지 않는 email, registrationId 조합으로 조회하면 UserNotFoundException이 발생해야 한다.")
    public void shouldThrowUserNotFoundException_WhenUserNotFound() {
        UserPK id = new UserPK("test@gmail.com", "google");

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findById(id);
        });
    }

}
