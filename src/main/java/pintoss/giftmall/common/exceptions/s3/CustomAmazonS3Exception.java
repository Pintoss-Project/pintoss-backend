package pintoss.giftmall.common.exceptions.s3;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.ErrorCode;

import java.time.LocalDateTime;

public class CustomAmazonS3Exception extends AmazonS3Exception {

    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final ErrorCode customErrorCode;
    private final LocalDateTime timestamp;

    public CustomAmazonS3Exception(HttpStatus httpStatus, ErrorCode customErrorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.customErrorCode = customErrorCode;
        this.timestamp = LocalDateTime.now();
    }

}
