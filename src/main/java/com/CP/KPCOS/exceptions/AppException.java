package com.CP.KPCOS.exceptions;


import com.CP.KPCOS.shared.enums.ResponseEnum;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class AppException  extends RuntimeException {

    public AppException(ResponseEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    private ResponseEnum errorCode;
}
