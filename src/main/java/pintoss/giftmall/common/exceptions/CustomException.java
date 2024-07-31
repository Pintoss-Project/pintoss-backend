package pintoss.giftmall.common.exceptions;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    private LocalDateTime timestamp;

    @Builder
    public CustomException(String message, HttpStatus httpStatus, ErrorCode errorCode, LocalDateTime timestamp) {
        super(errorCode.getMessage() + message);
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public CustomException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "httpStatus=" + httpStatus +
                ", timestamp=" + timestamp +
                ", message=" + getMessage() +
                '}';
    }
}
