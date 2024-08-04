package pintoss.giftmall.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardType {

    NOTICE("공지사항"),
    FAQS("자주묻는질문");

    private final String type;

}
