package pintoss.giftmall.common.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND("자원을 찾을 수 없습니다."),
    NOT_FOUND_ID("자원을 찾을 수 없습니다. ID : "),
    INVALID_REQUEST("유효하지 않은 요청입니다."),
    CREATION_FAILURE("생성 실패"),
    UPDATE_FAILURE("업데이트 실패"),
    DELETION_FAILURE("삭제 실패");

    private final String message;

}
