package kr.rewordit.api.exception;

import kr.rewordit.api.common.CommonRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonRes<Void>> handleCustomException(CustomException ex) {

        log.error("{} : {}", ex.getClass().getSimpleName(), ex.getMessage());

        return new ResponseEntity<>(
            CommonRes.failure(ex.getMessage()),
            ex.getStatus() == null ? HttpStatus.INTERNAL_SERVER_ERROR : ex.getStatus()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonRes<Void>> handleRuntimeException(RuntimeException ex) {
        log.error("{} : {}", ex.getClass().getSimpleName(), ex.getMessage());

        return new ResponseEntity<>(
            CommonRes.failure(ex.getMessage()),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonRes<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("{} : {}", ex.getClass().getSimpleName(), ex.getMessage());

        StringBuilder errorMessageBuilder = new StringBuilder();
        BindingResult bindingResult = ex.getBindingResult();

        if (bindingResult.hasFieldErrors()) {
            int fieldErrorCount = bindingResult.getFieldErrorCount();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            errorMessageBuilder.append("Fields [");

            for (int i = 0; i < fieldErrorCount; i++) {
                FieldError error = fieldErrors.get(i);

                if (i != 0) {
                    errorMessageBuilder.append(", ");
                }

                errorMessageBuilder.append(String.format("%s : %s", error.getField(), error.getDefaultMessage()));
            }

            errorMessageBuilder.append("]");
        }

        return new ResponseEntity<>(
            CommonRes.failure(errorMessageBuilder.toString()),
            HttpStatus.BAD_REQUEST
        );
    }

}
