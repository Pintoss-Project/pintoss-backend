package pintoss.giftmall.domains.order.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductRequest {

    @NotNull(message = "상품 ID는 필수 항목입니다.")
    private Long productId;

    @NotNull(message = "가격 카테고리 ID는 필수 항목입니다.")
    private Long priceCategoryId;

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;

    @Min(value = 1000, message = "가격은 1000원 이상이어야 합니다.")
    private int price;

    @Builder
    public OrderProductRequest(Long productId, Long priceCategoryId, int quantity, int price) {
        this.productId = productId;
        this.priceCategoryId = priceCategoryId;
        this.quantity = quantity;
        this.price = price;
    }

}
