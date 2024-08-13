package pintoss.giftmall.domains.cart.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.domains.cart.domain.Cart;

@Getter
public class CartResponse {

    private Long id;
    private Long productId;
    private Long priceCategoryId;
    private String name;
    private int price;
    private int quantity;
    private PayMethod payMethod;

    @Builder
    public CartResponse(Long id, Long productId, Long priceCategoryId, String name, int price, int quantity, PayMethod payMethod) {
        this.id = id;
        this.productId = productId;
        this.priceCategoryId = priceCategoryId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.payMethod = payMethod;
    }

    public static CartResponse fromEntity(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .productId(cart.getProduct().getId())
                .priceCategoryId(cart.getPriceCategory().getId())
                .name(cart.getProduct().getName())
                .price(cart.getPrice())
                .quantity(cart.getQuantity())
                .payMethod(cart.getPayMethod())
                .build();
    }
}
