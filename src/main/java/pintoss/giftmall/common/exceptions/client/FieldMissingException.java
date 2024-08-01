package pintoss.giftmall.common.exceptions.client;

import pintoss.giftmall.common.exceptions.ErrorCode;

public class FieldMissingException extends BadRequestException {

    public FieldMissingException(String fieldName) {
        super(ErrorCode.FIELD_MISSING_REQUEST, fieldName);
    }

}
