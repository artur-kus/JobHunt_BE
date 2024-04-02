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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private final static String DEFAULT_ERROR_MESSAGE = "unexpected.error";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponse> handleAllUnexpectedExceptions(Exception ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST, "unexpected", DEFAULT_ERROR_MESSAGE);
    }

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<DefaultResponse> handleDefaultException(DefaultException ex) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, "default", ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultResponse> handleNotFoundException(NotFoundException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND, "not.found", ex.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DefaultException handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new DefaultException(getErrors(ex));
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

    private Map<String, String> getErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
