package com.CP.KPCOS.controllers.authController;


import com.CP.KPCOS.dtos.request.LoginRequestApi;
import com.CP.KPCOS.dtos.response.BaseResponse;
import com.CP.KPCOS.dtos.response.object.LoginResponseApi;
import com.CP.KPCOS.services.AuthService.AuthService;
import com.nimbusds.jose.JOSEException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@RestController
@Tag(name = "Auth Controller", description = "Auth Controller")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/v1/auth")
public class ReadAuthController {

    private final AuthService authService;

    public ReadAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    Mono<BaseResponse<LoginResponseApi>> login(@RequestBody LoginRequestApi loginRequestApi) throws ParseException, JOSEException {
        LoginResponseApi loginResponseApi = authService.login(loginRequestApi.getUsername(), loginRequestApi.getPassword()).block();
        return Mono.just(BaseResponse.<LoginResponseApi>builder().data(loginResponseApi).build());
    }

}
