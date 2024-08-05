package pintoss.giftmall.common.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND("자원을 찾을 수 없습니다."),
    BAD_REQUEST("잘못된 요청입니다."),
    INVALID_REQUEST("유효하지 않은 요청입니다."),
    INVALID_FIELD_REQUEST("잘못된 필드 요청입니다."),
    FIELD_MISSING_REQUEST("필수 필드가 누락되었습니다."),
    CREATION_FAILURE("생성에 실패했습니다."),
    UPDATE_FAILURE("업데이트에 실패했습니다."),
    DELETION_FAILURE("삭제에 실패했습니다."),
    UNAUTHORIZED("인증이 필요합니다."),
    FORBIDDEN("권한이 없습니다."),
    CONFLICT("충돌이 발생했습니다."),
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다."),
    IMAGE_UPLOAD_ERROR("이미지 업로드에 실패했습니다."),
    ILLEGAL_REGISTRATION_ID("잘못된 등록 ID입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    INVALID_JWT_SIGNATURE("유효하지 않은 JWT 서명입니다."),
    EMPTY_FILE_EXCEPTION("업로드된 파일이 비어 있습니다. 다른 파일을 선택해 주세요."),
    INVALID_FILE_EXTENSION("유효하지 않은 파일 확장자입니다. 올바른 확장자를 가진 파일을 업로드해 주세요."),
    IO_EXCEPTION_ON_IMAGE_UPLOAD("이미지 업로드 중 오류가 발생했습니다. 다시 시도해 주세요."),
    IO_EXCEPTION_ON_IMAGE_DELETE("이미지 삭제 중 오류가 발생했습니다. 다시 시도해 주세요."),
    NO_FILE_EXTENSION("파일 확장자가 없습니다. 올바른 파일을 업로드해 주세요."),
    PUT_OBJECT_EXCEPTION("S3에 객체 업로드 중 오류가 발생했습니다.");


    private final String message;

}
