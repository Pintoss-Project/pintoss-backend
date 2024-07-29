package pintoss.giftmall.domains.product.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.domains.product.domain.PriceCategory;

import java.time.LocalDateTime;

@Getter
public class PriceCategoryResponse {

    private Long id;
    private String name;
    private int price;
    private int stock;
    private LocalDateTime createdAt;

    @Builder
    public PriceCategoryResponse(Long id, String name, int price, int stock, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
    }

    public static PriceCategoryResponse fromEntity(PriceCategory priceCategory) {
        return PriceCategoryResponse.builder()
                .id(priceCategory.getId())
                .name(priceCategory.getName())
                .price(priceCategory.getPrice())
                .stock(priceCategory.getStock())
                .build();
    }

}
