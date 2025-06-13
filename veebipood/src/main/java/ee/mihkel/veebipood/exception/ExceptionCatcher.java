package ee.mihkel.veebipood.exception;

import com.github.vladislavgoltjajev.personalcode.exception.PersonalCodeException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Date;

@RestControllerAdvice
@Log4j2
public class ExceptionCatcher {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> exceptionHandler(IdMisuseException e) {
        log.error(e.getMessage(), e);
        return getErrorMessageResponseEntity(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> exceptionHandler(PersonalCodeException e) {
        log.error(e.getMessage(), e);
        return getErrorMessageResponseEntity(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> exceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return getErrorMessageResponseEntity(e.getMessage());
    }

    private static ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(String e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
        errorMessage.setMessage(e);
        errorMessage.setTimestamp(new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorMessage);
    }
}

