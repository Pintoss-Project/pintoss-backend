package pintoss.giftmall.domains.product.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.domains.product.domain.Product;

import java.math.BigDecimal;

@Getter
public class SimpleProductResponse {

    private Long id;
    private String name;
    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;
    private String logoImageUrl;

    @Builder
    public SimpleProductResponse(Long id, String name, BigDecimal cardDiscount, BigDecimal phoneDiscount, String logoImageUrl) {
        this.id = id;
        this.name = name;
        this.cardDiscount = cardDiscount;
        this.phoneDiscount = phoneDiscount;
        this.logoImageUrl = logoImageUrl;
    }

    public static SimpleProductResponse fromEntity(Product product,String logoImageUrl) {
        return SimpleProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .cardDiscount(product.getCardDiscount())
                .phoneDiscount(product.getPhoneDiscount())
                .logoImageUrl(logoImageUrl)
                .build();
    }

}
