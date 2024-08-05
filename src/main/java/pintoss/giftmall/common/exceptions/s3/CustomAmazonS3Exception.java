package pintoss.giftmall.common.exceptions.s3;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pintoss.giftmall.common.exceptions.ErrorCode;

import java.time.LocalDateTime;

public class CustomAmazonS3Exception extends AmazonS3Exception {

    @Getter
    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;
    private final LocalDateTime timestamp;

    public CustomAmazonS3Exception(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }

}
