package pintoss.giftmall.common.exceptions;

import org.springframework.http.HttpStatus;

public class AuthException extends CustomException {

    public AuthException(ErrorCode errorCode, String message) {
        super(HttpStatus.UNAUTHORIZED, errorCode, errorCode.getMessage());
    }

}
