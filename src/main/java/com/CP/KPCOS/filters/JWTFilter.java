package com.CP.KPCOS.filters;


import com.CP.KPCOS.exceptions.AppException;
import com.CP.KPCOS.exceptions.ErrorDetails;
import com.CP.KPCOS.shared.enums.ResponseEnum;
import com.CP.KPCOS.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtil;

    private final RedisTemplate<String, String> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final List<Pair<String, String>> bypassToken = Arrays.asList(
                Pair.of("/swagger-ui/index.html", "GET"),
                Pair.of("/v3/api-docs", "GET"),
                Pair.of("/swagger-ui/swagger-initializer.js", "GET"),
                Pair.of("/swagger-ui/favicon-16x16.png", "GET"),
                Pair.of("/swagger-ui/favicon-32x32.png", "GET"),
                Pair.of("/swagger-ui/swagger-ui.css", "GET"),
                Pair.of("/swagger-ui/index.css", "GET"),
                Pair.of("/swagger-ui/swagger-ui-bundle.js", "GET"),
                Pair.of("/swagger-ui/swagger-ui-standalone-preset.js", "GET"),
                Pair.of("/api-docs/swagger-config", "GET"),
                Pair.of("/api-docs", "GET"),
                Pair.of("/api/v1/auth/login", "POST"),
                Pair.of("/api/v1/auth/register", "POST"),
                Pair.of("/api/v1/test", "GET")
        );
        for (Pair<String, String> bypasstoken : bypassToken) {
            if (request.getServletPath().equals(bypasstoken.getFirst()) && request.getMethod().equals(bypasstoken.getSecond())) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
