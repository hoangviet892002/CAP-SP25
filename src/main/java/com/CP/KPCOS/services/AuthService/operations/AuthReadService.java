package com.CP.KPCOS.services.AuthService.operations;

import com.CP.KPCOS.dtos.response.object.LoginResponseApi;
import com.nimbusds.jose.JOSEException;
import reactor.core.publisher.Mono;

import java.text.ParseException;

public interface AuthReadService {
    Mono<LoginResponseApi> login(String username, String password) throws ParseException, JOSEException;
}
