package pintoss.giftmall.common.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pintoss.giftmall.common.exceptions.client.*;
import pintoss.giftmall.common.exceptions.server.ServerErrorException;
import pintoss.giftmall.common.responseobj.ApiErrorResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ApiErrorResponse> handleCustomException(CustomException ex) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                ex.getHttpStatus(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ApiErrorResponse> handleBadRequestException(BadRequestException ex) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                ex.getHttpStatus(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                ex.getHttpStatus(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ApiErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                ex.getHttpStatus(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<ApiErrorResponse> handleForbiddenException(ForbiddenException ex) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                ex.getHttpStatus(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());

            if (Objects.equals(error.getCode(), "NotNull") || Objects.equals(error.getCode(), "NotBlank")) {
                throw new FieldMissingException(error.getField());
            }

            if (Objects.equals(error.getCode(), "Pattern")) {
                throw new InvalidFieldException(error.getField(), error.getDefaultMessage());
            }
        }

        ApiErrorResponse errorResponse = ApiErrorResponse.withErrors(
                HttpStatus.BAD_REQUEST,
                "유효하지 않은 요청입니다.",
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        ApiErrorResponse errorResponse = ApiErrorResponse.withErrors(
                HttpStatus.BAD_REQUEST,
                "유효하지 않은 요청입니다.",
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public final ResponseEntity<ApiErrorResponse> handleInvalidFieldException(InvalidFieldException ex) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                ex.getHttpStatus(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(FieldMissingException.class)
    public final ResponseEntity<ApiErrorResponse> handleFieldMissingException(FieldMissingException ex) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                ex.getHttpStatus(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(ServerErrorException.class)
    public final ResponseEntity<ApiErrorResponse> handleInternalServerErrorException(ServerErrorException ex) {
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                ex.getHttpStatus(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

}
