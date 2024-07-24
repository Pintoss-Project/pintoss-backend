package pintoss.giftmall.common.responseobj;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final int code;
    private final HttpStatus status;
    private final String message;
    private final T data;

    @Builder
    public ApiResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(httpStatus, message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return of(httpStatus, httpStatus.name(), data);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus) {
        return of(httpStatus, null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> create() {
        return of(HttpStatus.CREATED);
    }

}
