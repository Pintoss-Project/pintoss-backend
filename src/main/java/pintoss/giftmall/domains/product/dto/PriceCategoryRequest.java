package pintoss.giftmall.domains.product.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;

@Getter
@NoArgsConstructor
public class PriceCategoryRequest {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @Min(value = 1000, message = "가격은 1000원 이상이어야 합니다.")
    private int price;

    @Min(value = 1, message = "재고는 1 이상이어야 합니다.")
    private int stock;

    @NotNull(message = "상품 ID는 필수 항목입니다.")
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
