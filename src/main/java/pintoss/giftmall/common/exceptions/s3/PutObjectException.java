package pintoss.giftmall.common.exceptions.s3;

import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.ErrorCode;

public class PutObjectException extends CustomAmazonS3Exception {

    public PutObjectException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.PUT_OBJECT_EXCEPTION, message);
    }

}
