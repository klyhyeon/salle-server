package com.salle.server.controller;

import com.salle.server.domain.entity.User;
import com.salle.server.domain.factory.oauth.GithubOAuth;
import com.salle.server.service.UserService;
import com.salle.server.utils.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2/login")
public class OAuth2Controller {

    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;


    public OAuth2Controller(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/github")
    public ResponseEntity<String> authLoginGithub(@AuthenticationPrincipal OAuth2User principal) {
        User user = userService.createUserOrReturnExistingUserByOauthType(principal, new GithubOAuth());
        String authToken = jwtTokenProvider.createAuthToken(user);
        return ResponseEntity.ok(authToken);
    }
}
