package com.CP.KPCOS.services.AuthService.impl.operations;

import com.CP.KPCOS.dtos.response.object.RegisterResponseApi;
import com.CP.KPCOS.entity.RoleEntity;
import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.exceptions.AppException;
import com.CP.KPCOS.repository.UserRepository.UserRepository;
import com.CP.KPCOS.services.AuthService.operations.AuthCreateService;
import com.CP.KPCOS.services.RoleService.RoleService;
import com.CP.KPCOS.shared.enums.ResponseEnum;
import com.CP.KPCOS.utils.JwtUtils;
import com.nimbusds.jose.JOSEException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Service
public class AuthCreateServiceImpl implements AuthCreateService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleService roleService;

    public AuthCreateServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.roleService = roleService;
    }


    @Override
    public Mono<RegisterResponseApi> register(String username, String email, String password) throws ParseException, JOSEException {
        // check existing user
        if (userRepository.findByUsername(username) != null) {
            throw  new AppException(ResponseEnum.USER_ALREADY_EXISTS);
        }
        if ( userRepository.findByEmail(email) != null) {
            throw new AppException(ResponseEnum.EMAIL_ALREADY_EXISTS);
        }
        RoleEntity role = roleService.getCustomerRole();
        // create new user
         UserEntity userEntity = new UserEntity(username, passwordEncoder.encode(password), email , role);
        userRepository.save(userEntity);
        RegisterResponseApi registerResponseApi = new RegisterResponseApi();
        registerResponseApi.setUsername(userEntity.getUsername());
        registerResponseApi.setEmail(userEntity.getEmail());
        registerResponseApi.setRole(userEntity.getRole().getName());
        return Mono.just(registerResponseApi);
    }
}
