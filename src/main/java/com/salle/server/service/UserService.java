package com.salle.server.service;

import com.salle.server.domain.entity.User;
import com.salle.server.domain.enumeration.OauthType;
import com.salle.server.domain.factory.oauth.OAuthFactory;
import com.salle.server.repository.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User createUserOrReturnExistingUserByOauthType(OAuth2User principal, OAuthFactory oauthFactory) {
        User user = oauthFactory.createUser(principal);
        return createUserOrGetExistingUser(user);
    }

    private User createUserOrGetExistingUser(User user) {
        OauthType userOauthType = user.getOauthType();
        String userOauthId = user.getOauthId();
        return userRepository.findByOauthTypeAndOauthId(userOauthType, userOauthId)
                .orElseGet(() -> saveUser(user));
    }
}
