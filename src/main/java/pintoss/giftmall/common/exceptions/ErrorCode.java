package pintoss.giftmall.common.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND("자원을 찾을 수 없습니다."),
    INVALID_REQUEST("유효하지 않은 요청입니다."),
    CREATION_FAILURE("생성에 실패했습니다."),
    UPDATE_FAILURE("업데이트에 실패했습니다."),
    DELETION_FAILURE("삭제에 실패했습니다.");

    private final String message;

}
