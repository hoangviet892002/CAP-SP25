package com.CP.KPCOS.dtos.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BaseResponse <T>{
    @JsonProperty("success")
    private boolean isSuccess = true;
    @JsonProperty("data")
    private T data;
    @JsonProperty("message")
    private String message;
    public BaseResponse(T data) {
        this.data = data;
    }
}
