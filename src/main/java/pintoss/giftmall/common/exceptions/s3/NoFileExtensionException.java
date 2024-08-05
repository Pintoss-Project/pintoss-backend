package pintoss.giftmall.common.exceptions.s3;

import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.ErrorCode;

public class NoFileExtensionException extends CustomAmazonS3Exception {

    public NoFileExtensionException(String message) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.NO_FILE_EXTENSION, message);
    }

}
