package pintoss.giftmall.common.exceptions.s3;

import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.ErrorCode;

public class EmptyFileException extends CustomAmazonS3Exception {

    public EmptyFileException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.EMPTY_FILE_EXCEPTION, message);
    }

}
