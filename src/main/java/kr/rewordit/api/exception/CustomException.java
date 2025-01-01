package kr.rewordit.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    @Getter
    private HttpStatus status;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(HttpStatus status, String message) {
        super(message);

        this.status = status;
    }
}
