package com.salle.server.domain.factory.oauth;

import com.salle.server.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GithubOAuthTest {


    @Test
    @DisplayName("Github 유저 생성하기")
    void createUser() {
        GithubOAuth githubOAuth = new GithubOAuth();
        OAuth2User oauth2UserAttributes = new DefaultOAuth2User(
                null,
                Map.of("id", 123456, "login", "klyhyeon"),
                "login"
        );
        User githubUser = githubOAuth.createUser(oauth2UserAttributes);
        assertThat(githubUser.getOauthId()).isEqualTo("123456");
    }

}