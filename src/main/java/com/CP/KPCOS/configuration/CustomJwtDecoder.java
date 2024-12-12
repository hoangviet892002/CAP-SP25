package com.CP.KPCOS.configuration;

import com.CP.KPCOS.utils.JwtUtils;
import com.nimbusds.jwt.SignedJWT;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    private final JwtUtils jwtUtil;

    private final RedisTemplate<String, String> redisTemplate;

    public CustomJwtDecoder(JwtUtils jwtUtil, RedisTemplate<String, String> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);


            jwtUtil.verifyToken(token, false);

            return new Jwt(token,
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().getClaims()
            );

        } catch (Exception e) {
            throw new JwtException("Invalid token");
        }
    }
}
