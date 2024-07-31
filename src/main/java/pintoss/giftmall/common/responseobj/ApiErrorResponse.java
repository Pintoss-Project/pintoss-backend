package pintoss.giftmall.common.responseobj;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiErrorResponse {

    private final int code;
    private final HttpStatus status;
    private final String message;
    private final String errorCode;
    private final LocalDateTime timestamp;

    @Builder
    private ApiErrorResponse(HttpStatus status, String message, String errorCode, LocalDateTime timestamp) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    public static ApiErrorResponse of(HttpStatus status, String message, String errorCode, LocalDateTime timestamp) {
        return ApiErrorResponse.builder()
                .status(status)
                .message(message)
                .errorCode(errorCode)
                .timestamp(timestamp)
                .build();
    }

    public static ApiErrorResponse of(HttpStatus status, String message, String errorCode) {
        return of(status, message, errorCode, LocalDateTime.now());
    }

    public static ApiErrorResponse of(HttpStatus status, String message) {
        return of(status, message, null);
    }

    public static ApiErrorResponse of(HttpStatus status) {
        return of(status, status.getReasonPhrase());
    }

}
