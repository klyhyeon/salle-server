package com.salle.server.domain.factory.oauth;

import com.salle.server.domain.entity.User;
import com.salle.server.domain.enumeration.OauthType;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Objects;

public class GithubOAuth implements OAuthFactory {

    private static final OauthType OAUTH_TYPE = OauthType.GITHUB;

    @Override
    public User createUser(OAuth2User principal) {
        Integer oauthIdInteger = Objects.requireNonNull(principal.getAttribute("id"));
        String oauthIdString = String.valueOf(oauthIdInteger);
        String oauthLogin = principal.getAttribute("login");
        return User.builder()
                .oauthId(oauthIdString)
                .oauthLogin(oauthLogin)
                .oauthType(OAUTH_TYPE)
                .build();
    }
}
