package com.salle.server.interceptor;

import com.salle.server.domain.enumeration.ErrorCode;
import com.salle.server.error.SlErrorException;
import com.salle.server.utils.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    public static final String OAUTH_LOGIN = "oauthLogin";
    public static final String OAUTH_TYPE = "oauthType";
    public static final String USER_ID = "userId";

    private JwtTokenProvider jwtTokenProvider;

    public AuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (isMethodOption(request)) {
            return true;
        }

        String authToken = request.getHeader(AUTHORIZATION);
        ifTokenNotExistsThrowException(authToken);
        Map<String, String> claimMap = jwtTokenProvider.getClaims(authToken);
        request.setAttribute(USER_ID, claimMap.get(USER_ID));
        return true;
    }

    private boolean isMethodOption(HttpServletRequest request) {
        return request.getMethod().equals("OPTIONS");
    }

    private void ifTokenNotExistsThrowException(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            throw new SlErrorException(ErrorCode.AUTH_WRONG_REQUEST);
        }
    }
}
