package pintoss.giftmall.common.exceptions.server;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;

@Getter
public class ImageUploadException extends CustomException {

    public ImageUploadException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.IMAGE_UPLOAD_ERROR, message);
    }

}
