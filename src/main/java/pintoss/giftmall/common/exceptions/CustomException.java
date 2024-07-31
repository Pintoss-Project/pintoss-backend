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
    private String errorMessage;
    private LocalDateTime timestamp;

    @Builder
    public CustomException(String message, HttpStatus httpStatus, ErrorCode errorCode, LocalDateTime timestamp) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorMessage = errorCode.getMessage();
        this.timestamp = timestamp;
    }

    public CustomException(String message) {
        super(message);
    }

}
