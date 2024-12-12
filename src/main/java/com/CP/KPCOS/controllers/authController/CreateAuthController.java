package com.CP.KPCOS.controllers.authController;

import com.CP.KPCOS.dtos.request.RegisterRequestApi;
import com.CP.KPCOS.dtos.response.BaseResponse;
import com.CP.KPCOS.dtos.response.object.RegisterResponseApi;
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
public class CreateAuthController {
    private final AuthService authService;

    public CreateAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    Mono<BaseResponse<RegisterResponseApi>> register(@RequestBody RegisterRequestApi registerRequestApi) throws ParseException, JOSEException {
        RegisterResponseApi registerResponseApi = authService.register(registerRequestApi.getUsername(), registerRequestApi.getEmail(), registerRequestApi.getPassword()).block();
        return Mono.just(BaseResponse.<RegisterResponseApi>builder().data(registerResponseApi).build());
    }
}
