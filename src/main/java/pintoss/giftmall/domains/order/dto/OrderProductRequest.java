package pintoss.giftmall.domains.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductRequest {

    private Long productId;
    private Long priceCategoryId;
    private int quantity;
    private int price;

    @Builder
    public OrderProductRequest(Long productId, Long priceCategoryId, int quantity, int price) {
        this.productId = productId;
        this.priceCategoryId = priceCategoryId;
        this.quantity = quantity;
        this.price = price;
    }
}
