package pintoss.giftmall.common.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 Bad Request
    BAD_REQUEST("4001", "잘못된 요청입니다."),
    INVALID_REQUEST("4002", "유효하지 않은 요청입니다."),
    INVALID_FIELD_REQUEST("4003", "잘못된 필드 요청입니다."),
    FIELD_MISSING_REQUEST("4004", "필수 필드가 누락되었습니다."),
    INVALID_TOKEN("4005", "유효하지 않은 토큰입니다."),
    INVALID_JWT_SIGNATURE("4006", "유효하지 않은 JWT 서명입니다."),

    // 401 Unauthorized
    UNAUTHORIZED("4011", "인증이 필요합니다."),

    // 403 Forbidden
    FORBIDDEN("4031", "권한이 없습니다."),

    // 404 Not Found
    NOT_FOUND("4041", "자원을 찾을 수 없습니다."),

    // 409 Conflict
    CONFLICT("4091", "충돌이 발생했습니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR("5001", "서버 내부 오류가 발생했습니다."),
    CREATION_FAILURE("5002", "생성에 실패했습니다."),
    UPDATE_FAILURE("5003", "업데이트에 실패했습니다."),
    DELETION_FAILURE("5004", "삭제에 실패했습니다."),
    IMAGE_UPLOAD_ERROR("5005", "이미지 업로드에 실패했습니다."),
    ILLEGAL_REGISTRATION_ID("5006", "잘못된 등록 ID입니다."),

    // Amazon S3 Errors
    EMPTY_FILE_EXCEPTION("S3001", "업로드된 파일이 비어 있습니다. 다른 파일을 선택해 주세요."),
    INVALID_FILE_EXTENSION("S3002", "유효하지 않은 파일 확장자입니다. 올바른 확장자를 가진 파일을 업로드해 주세요."),
    IO_EXCEPTION_ON_IMAGE_UPLOAD("S3003", "이미지 업로드 중 오류가 발생했습니다. 다시 시도해 주세요."),
    IO_EXCEPTION_ON_IMAGE_DELETE("S3004", "이미지 삭제 중 오류가 발생했습니다. 다시 시도해 주세요."),
    NO_FILE_EXTENSION("S3005", "파일 확장자가 없습니다. 올바른 파일을 업로드해 주세요."),
    PUT_OBJECT_EXCEPTION("S3006", "S3에 객체 업로드 중 오류가 발생했습니다.");

    private final String code;
    private final String message;

}

