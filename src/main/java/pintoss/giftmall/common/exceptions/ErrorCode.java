package pintoss.giftmall.common.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND("요청한 자원을 찾을 수 없습니다."),
    INVALID_REQUEST("유효하지 않은 요청입니다.");

    private final String message;

}
