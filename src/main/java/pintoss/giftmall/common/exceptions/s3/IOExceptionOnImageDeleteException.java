package pintoss.giftmall.common.exceptions.s3;

import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.ErrorCode;

public class IOExceptionOnImageDeleteException extends CustomAmazonS3Exception {

    public IOExceptionOnImageDeleteException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.IO_EXCEPTION_ON_IMAGE_DELETE, message);
    }

}
