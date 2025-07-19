package com.CP.KPCOS.services.app.AuthService;

import com.CP.KPCOS.dtos.response.object.LoginResponseApi;
import com.CP.KPCOS.dtos.response.object.RegisterResponseApi;
import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.exceptions.BadRequestException;
import com.CP.KPCOS.repository.UserRepository.UserRepository;
import com.CP.KPCOS.shared.enums.RoleEnum;
import com.CP.KPCOS.utils.JwtUtils;
import com.CP.KPCOS.shared.ErrorMessage;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Mono<RegisterResponseApi> register(String username, String email, String password)
    {
        if (userRepository.findByUsername(username) != null) {
            throw new BadRequestException(ErrorMessage.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.findByEmail(email) != null) {
            throw new BadRequestException(ErrorMessage.EMAIL_ALREADY_EXISTS);
        }
        // create new user
        UserEntity userEntity = new UserEntity(username, passwordEncoder.encode(password), email, RoleEnum.USER.name());
        userRepository.save(userEntity);
        RegisterResponseApi registerResponseApi = new RegisterResponseApi();
        registerResponseApi.setUsername(userEntity.getUsername());
        registerResponseApi.setEmail(userEntity.getEmail());
        registerResponseApi.setRole(userEntity.getRole());
        return Mono.just(registerResponseApi);
    }

    public Mono<LoginResponseApi> login(String username, String password, HttpServletResponse response) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new BadRequestException(ErrorMessage.INVALID_CREDENTIALS);
        }
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new BadRequestException(ErrorMessage.INVALID_CREDENTIALS);
        }
        jwtUtils.generateToken(userEntity, response);
        jwtUtils.generateRefreshToken(userEntity, response);
        // Trả về thông tin user
        return Mono.just(LoginResponseApi.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build());
    }

    public Mono<Void> logout(HttpServletResponse response) {
        // Xoá cookie accessToken và refreshToken
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
        return Mono.empty();
    }
}
