package com.CP.KPCOS.shared;

import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.repository.UserRepository.UserRepository;
import com.CP.KPCOS.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public CurrentUserArgumentResolver(JwtUtils jwtUtils, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) && parameter.getParameterType().equals(UserEntity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = null;
        // Lấy từ cookie
        if (request != null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        // Nếu không có, lấy từ header
        if ((token == null || token.isEmpty()) && request != null) {
            token = request.getHeader("Authorization");
        }
        if (token == null || token.isEmpty()) {
            return null;
        }
        try {
            String username = jwtUtils.getUsernameFromToken(token);
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }
} 