package pintoss.giftmall.common.exceptions.server;

import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;

public class ServerErrorException extends CustomException {

    public ServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, message);
    }

}
