package pintoss.giftmall.common.exceptions;

import org.springframework.http.HttpStatus;

public class TokenException extends CustomException {

    public TokenException(ErrorCode errorCode) {
        super(HttpStatus.UNAUTHORIZED, errorCode, errorCode.getMessage());
    }

}
