package com.CP.KPCOS.exceptions;



import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@ControllerAdvice
@EnableWebMvc
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "Bad Request",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "Handle generic exceptions",
                            summary = "Handle uncategorized exceptions",
                            value = """
                                    {
                                      "timestamp": "2024-05-23T12:00:00.000+00:00",
                                      "status": 400,
                                      "path": "/api/v1/...",
                                      "error": "Uncategorized Exception",
                                      "message": "An uncategorized exception occurred",
                                      "errors": []
                                    }
                                    """
                    )))
    public ResponseEntity<ErrorDetails> handlingRuntimeException(Exception exception, WebRequest request) {
        log.error("Exception: ", exception);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), BAD_REQUEST.value(),
                request.getDescription(false).replace("uri=", ""), "Uncategorized Exception", "An uncategorized exception occurred", List.of(exception.getMessage()));
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "Internal Server Error Response",
                            summary = "Handle exception when an application error occurs",
                            value = """
                                    {
                                      "timestamp": "2024-05-23T12:00:00.000+00:00",
                                      "status": 500,
                                      "path": "/api/v1/...",
                                      "error": "Internal Server Error",
                                      "message": "An internal server error occurred",
                                      "errors": []
                                    }
                                    """
                    )))
    public ResponseEntity<ErrorDetails> handlingAppException(AppException exception, WebRequest request) {
        log.error("Exception: ", exception);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false).replace("uri=", ""), "Internal Server Error", "An internal server error occurred", List.of(exception.getMessage()));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "Bad Request",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "Bad Request Response",
                            summary = "Handle exception when a bad request is made",
                            value = """
                                    {
                                      "timestamp": "2024-05-23T12:00:00.000+00:00",
                                      "status": 400,
                                      "path": "/api/v1/...",
                                      "error": "Bad Request",
                                      "message": "A bad request was made",
                                      "errors": []
                                    }
                                    """
                    )))
    public ResponseEntity<ErrorDetails> handlingBadRequestException(BadRequestException exception, WebRequest request) {
        log.error("Exception: ", exception);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), BAD_REQUEST.value(),
                request.getDescription(false).replace("uri=", ""), "Bad Request", exception.getMessage(), List.of(exception.getMessage()));
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "API Error Response",
                            summary = "Handle exception when an API error occurs",
                            value = """
                                    {
                                        "timestamp": "2024-05-23T12:00:00.000+00:00",
                                        "status": 500,
                                        "path": "/api/v1/...",
                                        "error": "API Error",
                                        "message": "An error occurred while processing the API request",
                                        "errors": []
                                        }
                                        """
                    )))
    public ResponseEntity<ErrorDetails> handlingApiException(ApiException exception, WebRequest request) {
        log.error("Exception: ", exception);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false).replace("uri=", ""), "API Error", exception.getMessage(), List.of(exception.getMessage()));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @ExceptionHandler(ApplicationExeption.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "Application Error Response",
                            summary = "Handle exception when an application error occurs",
                            value = """
                                    {
                                      "timestamp": "2024-05-23T12:00:00.000+00:00",
                                      "status": 500,
                                      "path": "/api/v1/...",
                                      "error": "Application Error",
                                      "message": "An application error occurred",
                                      "errors": []
                                    }
                                    """
                    )))
    public ResponseEntity<ErrorDetails> handlingApplicationException(ApplicationExeption exception, WebRequest request) {
        log.error("Exception: ", exception);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false).replace("uri=", ""), "Application Error", exception.getMessage(), List.of(exception.getMessage()));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Not Found",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            name = "Not Found Response",
                            summary = "Handle exception when a resource is not found",
                            value = """
                                    {
                                        "timestamp": "2024-05-23T12:00:00.000+00:00",
                                        "status": 404,
                                        "path": "/api/v1/...",
                                        "error": "Not Found",
                                        "message": "The requested resource was not found",
                                        "errors": []
                                        }
                                    """
                                    )))
    public ResponseEntity<ErrorDetails> handlingNotFoundException(NotFoundException exception, WebRequest request) {
        log.error("Exception: ", exception);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), NOT_FOUND.value(),
                request.getDescription(false).replace("uri=", ""), "Not Found", exception.getMessage(), List.of(exception.getMessage()));
        return ResponseEntity.status(NOT_FOUND).body(errorDetails);
    }


}
