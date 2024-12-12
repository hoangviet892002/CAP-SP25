package com.CP.KPCOS.services.AuthService.operations;

import com.CP.KPCOS.dtos.response.object.RegisterResponseApi;
import com.nimbusds.jose.JOSEException;
import reactor.core.publisher.Mono;

import java.text.ParseException;

public interface AuthCreateService {
    Mono<RegisterResponseApi> register(String username, String email, String password) throws ParseException, JOSEException;
}
