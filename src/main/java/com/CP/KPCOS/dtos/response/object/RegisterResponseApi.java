package com.CP.KPCOS.dtos.response.object;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterResponseApi {
    private String username;
    private String email;
    private String role;
}
