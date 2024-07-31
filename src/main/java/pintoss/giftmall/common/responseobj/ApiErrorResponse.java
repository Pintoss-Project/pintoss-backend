package pintoss.giftmall.common.responseobj;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiErrorResponse {

    private final int code;
    private final HttpStatus status;
    private final String errorMessage;
    private final LocalDateTime timestamp;

    @Builder
    private ApiErrorResponse(HttpStatus status, String errorMessage, LocalDateTime timestamp) {
        this.code = status.value();
        this.status = status;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }

    public static ApiErrorResponse of(HttpStatus status, String errorMessage, LocalDateTime timestamp) {
        return ApiErrorResponse.builder()
                .status(status)
                .errorMessage(errorMessage)
                .timestamp(timestamp)
                .build();
    }

    public static ApiErrorResponse of(HttpStatus status, String errorMessage) {
        return of(status, errorMessage, LocalDateTime.now());
    }

    public static ApiErrorResponse of(HttpStatus status) {
        return of(status, status.getReasonPhrase());
    }

}
