package com.salle.server.utils;

import com.salle.server.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    public final static String SECRET_KEY = "fceeae51e1bda03cac21a8bcc112fe2dd10d4d1bd64714118db74f4cec664e3b";
    public static final String OAUTH_LOGIN = "oauthLogin";
    public static final String OAUTH_TYPE = "oauthType";
    public static final String USER_ID = "userId";

    public String createAuthToken(User user) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, String> claimMap = new HashMap<>();
        String oauthLogin = user.getOauthLogin();
        String oauthType = user.getOauthType().name();
        String userId = String.valueOf(user.getId());
        claimMap.put(OAUTH_LOGIN, oauthLogin);
        claimMap.put(OAUTH_TYPE, oauthType);
        claimMap.put(USER_ID, userId);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(claimMap)
                .signWith(signingKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }

    public Map<String, String> getClaims(String authToken) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(authToken)
                .getBody();

        Map<String, String> map = new HashMap<>();
        map.put(OAUTH_LOGIN, claims.get(OAUTH_LOGIN, String.class));
        map.put(OAUTH_TYPE, claims.get(OAUTH_TYPE, String.class));
        map.put(USER_ID, claims.get(USER_ID, String.class));
        return map;
    }

}

