package pintoss.giftmall.domains.cart.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.domains.cart.domain.Cart;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.user.domain.User;

@Getter
@NoArgsConstructor
public class CartRequest {

//    @NotNull(message = "상품 ID는 필수 항목입니다.")
//    private Long productId;

    @NotNull(message = "가격 카테고리 ID는 필수 항목입니다.")
    private Long priceCategoryId;

    @NotBlank(message = "상품 이름은 필수 항목입니다.")
    private String name;

    @Min(value = 1, message = "수량은 최소 1 이상이어야 합니다.")
    private int quantity;

    @NotNull(message = "결제 방법은 필수 항목입니다.")
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Builder
    public CartRequest(Long priceCategoryId, String name, int quantity, PayMethod payMethod) {
        this.priceCategoryId = priceCategoryId;
        this.name = name;
        this.quantity = quantity;
        this.payMethod = payMethod;
    }

    public Cart toEntity(Product product, PriceCategory priceCategory, User user) {
        return Cart.builder()
                .product(product)
                .priceCategory(priceCategory)
                .user(user)
                .quantity(quantity)
                .payMethod(payMethod)
                .build();
    }

}
