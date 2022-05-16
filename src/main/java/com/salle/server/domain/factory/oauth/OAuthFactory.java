package com.salle.server.domain.factory.oauth;

import com.salle.server.domain.entity.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthFactory {

    User createUser(OAuth2User principal);
}
