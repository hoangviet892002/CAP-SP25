package com.CP.KPCOS.services.sync;

import com.CP.KPCOS.exceptions.ApplicationExeption;

public class SyncDataExeption extends ApplicationExeption {
    public SyncDataExeption(String message) {
        super(message);
    }

    public SyncDataExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
