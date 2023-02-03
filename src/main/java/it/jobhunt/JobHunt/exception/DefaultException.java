package it.jobhunt.JobHunt.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DefaultException extends Exception {

    private String message;

    public DefaultException(String message) {
        super(message);
    }
}
