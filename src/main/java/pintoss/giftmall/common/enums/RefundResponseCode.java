package pintoss.giftmall.common.enums;

public enum RefundResponseCode {
    SUCCESS("0000", "����"),
    INVALID_APPROVAL_CODE("0104", "���ι�ȣ�� �߸��Ǿ����ϴ�"),
    SYSTEM_ERROR("9999", "�ý��� ����"),
    PARTIAL_REFUND_NOT_ALLOWED("0125", "�κ� ȯ�� �Ұ�"),
    ALREADY_CANCELLED("0101", "�̹� ��ҵ� �ŷ�");

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
