package it.jobhunt.JobHunt.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends Exception {
    private String message;

    public <T> NotFoundException(Class<T> objectClass) {
        super();
        this.message = "not.found." + objectClass.getSimpleName();
    }
}