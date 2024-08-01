package pintoss.giftmall.domains.cart.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.cart.domain.Cart;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.user.domain.User;

@Getter
@NoArgsConstructor
public class CartRequest {

    @NotNull(message = "상품 ID는 필수 항목입니다.")
    private Long productId;

    @NotBlank(message = "상품 이름은 필수 항목입니다.")
    private String name;

    @Min(value = 1000, message = "가격은 1000원 이상이어야 합니다.")
    private int price;

    @Min(value = 1, message = "수량은 최소 1 이상이어야 합니다.")
    private int quantity;

    @NotBlank(message = "결제 방법은 필수 항목입니다.")
    private String payMethod;

    @Builder
    public CartRequest(Long productId, String name, int price, int quantity, String payMethod) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.payMethod = payMethod;
    }

    public Cart toEntity(Product product, User user) {
        return Cart.builder()
                .product(product)
                .user(user)
                .quantity(quantity)
                .price(price)
                .payMethod(payMethod)
                .build();
    }

}
