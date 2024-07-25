package pintoss.giftmall.domains.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;

@Getter
@NoArgsConstructor
public class PriceCategoryRequest {

    @NotBlank
    private String name;

    @NotNull
    private int price;

    @NotNull
    private int stock;

    @NotNull
    private Long productId;

    @Builder
    public PriceCategoryRequest(String name, int price, int stock, Long productId) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.productId = productId;
    }

    public PriceCategory toEntity(Product product) {
        return PriceCategory.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .product(product)
                .build();
    }

}
