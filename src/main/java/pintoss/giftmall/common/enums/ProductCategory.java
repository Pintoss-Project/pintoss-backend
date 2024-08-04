package pintoss.giftmall.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCategory {

    CBM("문화/도서/영화"),
    GO("게임/온라인콘텐츠"),
    LS("생활/쇼핑");

    private final String category;

}
