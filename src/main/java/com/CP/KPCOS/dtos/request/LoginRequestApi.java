package com.CP.KPCOS.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestApi {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

}
