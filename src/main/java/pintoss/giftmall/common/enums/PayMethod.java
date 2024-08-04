package pintoss.giftmall.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayMethod {

    CARD("카드"),
    PHONE("휴대폰");

    private final String method;

}
