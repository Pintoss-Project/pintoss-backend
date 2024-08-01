package pintoss.giftmall.common.exceptions.client;

import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;

public class ForbiddenException extends CustomException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, message);
    }

}
