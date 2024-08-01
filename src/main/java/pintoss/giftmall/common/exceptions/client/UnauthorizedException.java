package pintoss.giftmall.common.exceptions.client;

import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED, message);
    }

}
