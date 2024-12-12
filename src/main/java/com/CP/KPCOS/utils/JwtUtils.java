package com.CP.KPCOS.utils;


import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.exceptions.AppException;
import com.CP.KPCOS.shared.enums.ResponseEnum;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor

public class JwtUtils {
    @Value("${spring.application.jwt.secret}")
    private String secret;
    @Value("${spring.application.jwt.expiration}")
    private long expiration;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String TOKEN_BLACKLIST_PREFIX = "blacklist:";
    private static final String ACTIVE_TOKEN_PREFIX = "active:";


    @NonFinal
    @Value("${spring.application.jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${spring.application.jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public void verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        // Check if token is in blacklist
        if (isTokenBlacklisted(token)) {
            throw new AppException(ResponseEnum.TOKEN_INVALID);
        }

        byte[] key = secret.getBytes(StandardCharsets.UTF_8);
        if (key.length < 32) {
            key = Arrays.copyOf(key, 32);
        }
        JWSVerifier jwsVerifier = new MACVerifier(key);
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(jwsVerifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ResponseEnum.TOKEN_INVALID);
        }

    }


    private void storeActiveToken (String token) {
        redisTemplate.opsForValue().set(ACTIVE_TOKEN_PREFIX + token, true, expiration);
    }


    private String buildScope(UserEntity user) {
        return user.getRole().getName();

    }
    private  boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(TOKEN_BLACKLIST_PREFIX + token));
    }
    private String generateToken(JWTClaimsSet claimsSet) {
        try {
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claimsSet.toJSONObject()));
            log.info("Signing token with secret: {}", secret);
            byte[] key = secret.getBytes(StandardCharsets.UTF_8);
            if (key.length < 32) {
                key = Arrays.copyOf(key, 32);
            }

            jwsObject.sign(new MACSigner(key));

            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error while signing token", e);
        }
    }


    public String generateToken (UserEntity user) {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("com.templateredis")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("user_id", user.getId())
                .claim("email", user.getEmail())
                .build();
        String token = generateToken(jwtClaimsSet);
        storeActiveToken(token);
        return token;
    }

    public Date getExpiryDate(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.getJWTClaimsSet().getExpirationTime();
    }

    public String formatExpiryTime(Date expiryDate) {
        LocalDateTime expiryLocalDateTime = LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return expiryLocalDateTime.format(formatter);
    }

    public boolean isValidToken(String token) {
        try {
            verifyToken(token, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void logout(String token) {
        redisTemplate.opsForValue().set(TOKEN_BLACKLIST_PREFIX + token, true, expiration);
    }


}
