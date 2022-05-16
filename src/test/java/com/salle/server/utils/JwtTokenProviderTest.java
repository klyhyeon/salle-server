package com.salle.server.utils;

import com.salle.server.domain.entity.User;
import com.salle.server.domain.enumeration.OauthType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = JwtTokenProvider.class)
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Jwt 토큰생성")
    void createJwtToken() {
        User user = User.builder()
                .oauthId("9283")
                .oauthLogin("klyhyeon")
                .oauthType(OauthType.GITHUB).build();
        String newToken = jwtTokenProvider.createAuthToken(user);
        assertThat(newToken).isNotEmpty();
    }

    @Test
    @DisplayName("Jwt 토큰확인")
    void getClaimsJwtToken() {
        User user = User.builder()
                .oauthId("9283")
                .oauthLogin("klyhyeon")
                .oauthType(OauthType.GITHUB).build();
        String newToken = jwtTokenProvider.createAuthToken(user);
        Map<String, String> claimMap = jwtTokenProvider.getClaims(newToken);
        assertThat(claimMap.get("oauthLogin")).isEqualTo("klyhyeon");
    }
}