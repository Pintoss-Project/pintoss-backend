package pintoss.giftmall.common.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    private LocalDateTime timestamp;

    public CustomException(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + " : " + message);
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }

    public CustomException(HttpStatus httpStatus, String message, ErrorCode errorCode) {
        super(message + " : " + errorCode.getMessage());
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }

    public CustomException(String message) {
        super(message);
    }

}
