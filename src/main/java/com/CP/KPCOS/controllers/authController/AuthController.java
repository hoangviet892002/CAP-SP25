package com.CP.KPCOS.controllers.authController;

import com.CP.KPCOS.dtos.request.LoginRequestApi;
import com.CP.KPCOS.dtos.request.RegisterRequestApi;
import com.CP.KPCOS.dtos.response.BaseResponse;
import com.CP.KPCOS.dtos.response.object.LoginResponseApi;
import com.CP.KPCOS.dtos.response.object.RegisterResponseApi;
import com.CP.KPCOS.entity.UserEntity;
import com.CP.KPCOS.services.app.AuthService.AuthService;
import com.CP.KPCOS.shared.CurrentUser;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@Tag(name = "Auth Controller", description = "Auth Controller")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Mono<BaseResponse<RegisterResponseApi>> register(@RequestBody RegisterRequestApi registerRequestApi) throws ParseException, JOSEException {
        RegisterResponseApi registerResponseApi = authService.register(registerRequestApi.getUsername(), registerRequestApi.getEmail(), registerRequestApi.getPassword()).block();
        return Mono.just(new BaseResponse<>(registerResponseApi));
    }

    @PostMapping("/login")
    public Mono<BaseResponse<LoginResponseApi>> login(@RequestBody LoginRequestApi loginRequestApi, HttpServletResponse response) {
        return authService.login(loginRequestApi.getUsername(), loginRequestApi.getPassword(), response)
                .map(BaseResponse::new);
    }

    @GetMapping("/me")
    public Mono<BaseResponse<LoginResponseApi>> getCurrentUser(@CurrentUser UserEntity user) {
        LoginResponseApi loginResponseApi = LoginResponseApi.toResponse(user);
        return Mono.just(new BaseResponse<>(loginResponseApi));
    }

    @PostMapping("/logout")
    public Mono<BaseResponse<String>> logout(HttpServletResponse response) {
        authService.logout(response);
        return Mono.just(new BaseResponse<>("Logout successful"));
    }
} 