package com.CP.KPCOS.dtos.response.object;

import com.CP.KPCOS.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponseApi {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private String role;

    public static LoginResponseApi toResponse(UserEntity userEntity) {
        return LoginResponseApi.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }
}
