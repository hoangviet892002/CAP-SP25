package com.CP.KPCOS.dtos.response.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponseApi {
    @JsonProperty("token")
    private String token;
}
