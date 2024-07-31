package pintoss.giftmall.common.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pintoss.giftmall.common.responseobj.ApiErrorResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ApiErrorResponse> handleCustomException(CustomException ex, WebRequest request) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                ex.getHttpStatus(),
                ex.getMessage(),
                ex.getErrorCode().name(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

}
