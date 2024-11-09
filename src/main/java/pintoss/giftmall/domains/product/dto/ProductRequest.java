package pintoss.giftmall.domains.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pintoss.giftmall.common.enums.ProductCategory;
import pintoss.giftmall.domains.product.domain.Product;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "상품 이름은 필수 항목입니다.")
    private String name;

    @NotNull(message = "인기 여부는 필수 항목입니다.")
    @JsonProperty("isPopular")
    private boolean isPopular = false;

    @NotNull(message = "카드 할인 금액은 필수 항목입니다.")
    @Min(value = 0, message = "카드 할인 금액은 0 이상이어야 합니다.")
    private BigDecimal cardDiscount = BigDecimal.ZERO;

    @NotNull(message = "전화 할인 금액은 필수 항목입니다.")
    @Min(value = 0, message = "전화 할인 금액은 0 이상이어야 합니다.")
    private BigDecimal phoneDiscount = BigDecimal.ZERO;

    @NotBlank(message = "홈페이지 주소는 필수 항목입니다.")
    private String homePage;

    @NotBlank(message = "고객 센터 정보는 필수 항목입니다.")
    private String csCenter;

    @NotBlank(message = "설명은 필수 항목입니다.")
    private String description;

    @NotBlank(message = "발행자는 필수 항목입니다.")
    private String publisher;

    @NotNull(message = "카테고리는 필수 항목입니다.")
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @NotBlank(message = "로고 이미지는 필수 항목입니다.")
    private String logoImageUrl;

    @NotBlank(message = "상품 유의사항은 필수 항목입니다.")
    private String note;
    //상품권의 인덱스
    private int index;

    @Builder
    public ProductRequest(String name, boolean isPopular, BigDecimal cardDiscount, BigDecimal phoneDiscount,
                          String homePage, String csCenter, String description, String publisher, ProductCategory category, String note, String logoImageUrl,
                          int index) {
        this.name = name;
        this.isPopular = isPopular;
        this.cardDiscount = cardDiscount;
        this.phoneDiscount = phoneDiscount;
        this.homePage = homePage;
        this.csCenter = csCenter;
        this.description = description;
        this.publisher = publisher;
        this.category = category;
        this.note = note;
        this.logoImageUrl = logoImageUrl;
        this.index = index;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .isPopular(isPopular)
                .cardDiscount(cardDiscount)
                .phoneDiscount(phoneDiscount)
                .homePage(homePage)
                .csCenter(csCenter)
                .description(description)
                .publisher(publisher)
                .category(category)
                .note(note)//추가한 부분.
                .build();
    }

}
