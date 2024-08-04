package pintoss.giftmall.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayStatus {

    PENDING("결제대기"),
    COMPLETED("결제완료"),
    CANCELLED("결제취소"),
    FAILED("결제실패"),
    REFUND_IN_PROGRESS("환불진행중"),
    REFUNDED("환불완료"),
    ON_HOLD("결제보류"),
    DECLINED("결제거절"),
    PARTIALLY_REFUNDED("결제부분환불");

    private final String description;

}
