package com.CP.KPCOS.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@Schema(description = "Error details returned in case of exceptions")
public class ErrorDetails {
    @Schema(description = "Timestamp of the error")
    private Date timestamp;
    @Schema(description = "Status of the error")
    private int status;
    @Schema(description = "Path of the error")
    private String path;
    @Schema(description = "Error")
    private String error;
    @Schema(description = "Message of the error")
    private String message;
    @Schema(description = "List of errors")
    private List<String> errors;

    public ErrorDetails(Date timestamp, int status, String path, String error, String message, List<String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
        this.error = error;
        this.message = message;
        this.errors = errors;
    }
    public ErrorDetails(Date timestamp, int value, String message, String tokenIsRequired) {
    }
}
