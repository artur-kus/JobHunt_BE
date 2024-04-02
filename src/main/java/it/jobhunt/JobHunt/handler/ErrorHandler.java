package it.jobhunt.JobHunt.handler;


import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.NotFoundException;
import it.jobhunt.JobHunt.util.DefaultResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private final static String DEFAULT_ERROR_MESSAGE = "unexpected.error";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponse> handleAllUnexpectedExceptions(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException manve) {
            return handleException(ex, HttpStatus.BAD_REQUEST, "validation", getErrorMessage(manve));
        } else return handleException(ex, HttpStatus.BAD_REQUEST, "unexpected", DEFAULT_ERROR_MESSAGE);
    }

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<DefaultResponse> handleDefaultException(DefaultException ex) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, "default", ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultResponse> handleNotFoundException(NotFoundException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND, "not.found", ex.getMessage());
    }

    private ResponseEntity<DefaultResponse> handleException(Exception ex, HttpStatus httpStatus,
                                                            String errorMessageKey, String errorMessage) {
        log.error("{}.exception: {}", errorMessageKey, ex.getMessage());
        ex.printStackTrace();
        DefaultResponse defaultResponse = DefaultResponse.builder()
                .httpStatus(httpStatus)
                .message(errorMessage)
                .build();
        return new ResponseEntity<>(defaultResponse, httpStatus);
    }

    private String getErrorMessage(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(";"));
    }
}