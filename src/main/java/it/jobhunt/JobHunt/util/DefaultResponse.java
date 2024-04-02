package it.jobhunt.JobHunt.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
public class DefaultResponse {
    private int status;
    private String error;
    private String message;
    private Date timestamp;

    @Builder
    public DefaultResponse(HttpStatus httpStatus, String message) {
        status = httpStatus.value();
        error = httpStatus.getReasonPhrase();
        this.message = message;
        timestamp = new Date();
    }
}