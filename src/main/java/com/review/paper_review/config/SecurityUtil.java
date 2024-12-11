package com.review.paper_review.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class SecurityUtil {

    public static String getCurrentUserId() {
        String id = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.equals("anonymousUser")) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            id = (String) oAuth2User.getAttributes().get("email");
        }
        return id;
    }

}
