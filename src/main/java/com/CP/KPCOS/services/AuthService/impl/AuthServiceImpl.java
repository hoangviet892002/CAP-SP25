package com.CP.KPCOS.services.AuthService.impl;


import com.CP.KPCOS.dtos.response.object.LoginResponseApi;
import com.CP.KPCOS.dtos.response.object.RegisterResponseApi;
import com.CP.KPCOS.services.AuthService.AuthService;
import com.CP.KPCOS.services.AuthService.impl.operations.AuthCreateServiceImpl;
import com.CP.KPCOS.services.AuthService.impl.operations.AuthReadServiceImpl;
import com.nimbusds.jose.JOSEException;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthCreateServiceImpl authCreateServiceImpl;
    private final AuthReadServiceImpl authReadServiceImpl;

    @Autowired
    public AuthServiceImpl(AuthCreateServiceImpl authCreateServiceImpl, AuthReadServiceImpl authReadServiceImpl) {
        this.authCreateServiceImpl = authCreateServiceImpl;
        this.authReadServiceImpl = authReadServiceImpl;
    }


    @Override
    public Mono<LoginResponseApi> login(String username, String password) throws ParseException, JOSEException {
        return  authReadServiceImpl.login(username, password);
    }

    @Override
    public Mono<RegisterResponseApi> register(String username, String email, String password) throws ParseException, JOSEException {
        return authCreateServiceImpl.register(username, email, password);
    }
}
