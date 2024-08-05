package pintoss.giftmall.common.exceptions.s3;

import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.ErrorCode;

public class InvalidFileExtensionException extends CustomAmazonS3Exception {

    public InvalidFileExtensionException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_FILE_EXTENSION, message);
    }

}
