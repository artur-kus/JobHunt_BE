package it.jobhunt.JobHunt.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InternalException extends Exception {

    private String errorMessage = "internal.exception";

    public InternalException(String message) {
        super();
        this.errorMessage = message;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
