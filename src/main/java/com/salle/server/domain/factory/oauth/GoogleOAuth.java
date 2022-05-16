package com.salle.server.domain.factory.oauth;

import com.salle.server.domain.entity.User;
import com.salle.server.domain.enumeration.OauthType;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleOAuth implements OAuthFactory{

    private static final OauthType OAUTH_TYPE = OauthType.GOOGLE;

    @Override
    public User createUser(OAuth2User principal) {
        return null;
    }
}
