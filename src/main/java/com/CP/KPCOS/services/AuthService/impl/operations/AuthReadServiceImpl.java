package com.CP.KPCOS.services.AuthService.impl.operations;


import com.CP.KPCOS.dtos.response.object.LoginResponseApi;
import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.exceptions.AppException;
import com.CP.KPCOS.repository.UserRepository.UserRepository;
import com.CP.KPCOS.services.AuthService.operations.AuthReadService;
import com.CP.KPCOS.shared.enums.ResponseEnum;
import com.CP.KPCOS.utils.JwtUtils;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Service
public class AuthReadServiceImpl implements AuthReadService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final  JwtUtils jwtUtils;

    public AuthReadServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public Mono<LoginResponseApi> login(String username, String password)  throws ParseException, JOSEException {
        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            throw new AppException(ResponseEnum.WRONG_PASSWORD_OR_USERNAME);
        }

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new AppException(ResponseEnum.WRONG_PASSWORD_OR_USERNAME);
        }
        String token = jwtUtils.generateToken(userEntity);
        return Mono.just(new LoginResponseApi(token));

    }
}
