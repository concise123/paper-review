package com.review.paper_review.config;

import com.review.paper_review.domain.user.Role;
import com.review.paper_review.domain.user.User;
import com.review.paper_review.domain.user.UserPK;
import com.review.paper_review.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final DefaultOAuth2UserService delegate;

    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository; // 생성자 주입
        this.delegate = new DefaultOAuth2UserService(); // 직접 객체 생성
    }

    // 사용자 정보 가져와서 처리 후 반환
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // UserInfo Endpoint에서 사용자 정보 가져오기
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> userAttributes = oAuth2User.getAttributes();
        String email = (String) userAttributes.get("email");
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().
                getUserInfoEndpoint().getUserNameAttributeName();

        // 사용자 정보를 DB에서 찾기
        Optional<User> userOptional = userRepository.findById(new UserPK(email, registrationId));

        // DB에 사용자가 존재하면 가져오고 없으면 새로 저장
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = User.builder()
                    .email(email)
                    .registrationId(registrationId)
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRoleName())), // 사용자의 권한을 설정
                userAttributes,
                userNameAttributeName);
    }

}
