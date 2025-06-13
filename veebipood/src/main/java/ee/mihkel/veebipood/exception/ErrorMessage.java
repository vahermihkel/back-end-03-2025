package ee.mihkel.veebipood.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private int code;
    private String message;
    private Date timestamp;
}
