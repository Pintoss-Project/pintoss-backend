package pintoss.giftmall.common.exceptions.client;

import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, message);
    }

}
