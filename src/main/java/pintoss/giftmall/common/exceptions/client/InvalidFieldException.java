package pintoss.giftmall.common.exceptions.client;

import pintoss.giftmall.common.exceptions.ErrorCode;

public class InvalidFieldException extends BadRequestException {

    public InvalidFieldException(String fieldName, String message) {
        super(ErrorCode.INVALID_FIELD_REQUEST, fieldName + ": " + message);
    }

}
