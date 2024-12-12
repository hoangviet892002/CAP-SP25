package com.CP.KPCOS.controllers.testController;

import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@Tag(name = "Test Controller", description = "Test Controller")
@RequestMapping("/api/v1/test")
@SecurityRequirement(name = "Authorization")
public class TestControllers {

    @GetMapping()
    @ApiResponse(description = "Test")
    public String test() {
        return "Test";
    }

    @PostMapping()
    @ApiResponse(description = "Test")
    public String testPost() {
        return "Test";
    }
}
