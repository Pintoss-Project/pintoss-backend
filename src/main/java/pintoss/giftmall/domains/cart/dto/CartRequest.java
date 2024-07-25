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

    @NotNull
    private Long productId;

    @NotBlank
    private String name;

    @NotNull
    private int price;

    @NotNull
    private int quantity;

    @NotBlank
    private String checkoutMethod;

    @Builder
    public CartRequest(Long productId, String name, int price, int quantity, String checkoutMethod) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.checkoutMethod = checkoutMethod;
    }

    public Cart toEntity(Product product, User user) {
        return Cart.builder()
                .product(product)
                .user(user)
                .quantity(quantity)
                .price(price)
                .checkoutMethod(checkoutMethod)
                .build();
    }

}
