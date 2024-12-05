package pintoss.giftmall.common.enums;

public enum RefundResponseCode {
    SUCCESS("0000", "성공"),
    INVALID_APPROVAL_CODE("0104", "승인번호가 잘못되었습니다"),
    SYSTEM_ERROR("9999", "시스템 에러"),
    PARTIAL_REFUND_NOT_ALLOWED("0125", "부분 환불 불가"),
    ALREADY_CANCELLED("0101", "이미 취소된 거래");

    private final String code;
    private final String message;

    RefundResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
