package pintoss.giftmall.domains.cart.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.domains.cart.domain.Cart;

@Getter
public class CartResponse {

    private Long id;
    private String name;
    private int price;
    private int quantity;
    private String checkoutMethod;

    @Builder
    public CartResponse(Long id, String name, int price, int quantity, String checkoutMethod) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.checkoutMethod = checkoutMethod;
    }

    public static CartResponse fromEntity(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .name(cart.getProduct().getName())
                .price(cart.getPrice())
                .quantity(cart.getQuantity())
                .checkoutMethod(cart.getCheckoutMethod())
                .build();
    }

}
