package pintoss.giftmall.domains.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.product.domain.PriceCategory;

@Getter
@NoArgsConstructor
public class PriceCategoryRequest {

    @NotBlank
    private String name;

    @NotNull
    private int price;

    @NotNull
    private int stock;

    @Builder
    public PriceCategoryRequest(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public PriceCategory toEntity() {
        return PriceCategory.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .build();
    }

}
