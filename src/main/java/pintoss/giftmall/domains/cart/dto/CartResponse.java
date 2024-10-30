package pintoss.giftmall.domains.cart.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.domains.cart.domain.Cart;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.infra.PriceCategoryReader;
import pintoss.giftmall.domains.product.infra.ProductReader;

import java.math.BigDecimal;

@Getter
public class CartResponse {

    private Long id;
    private Long productId;
    private Long priceCategoryId;
    private String name;
    private int quantity;
    private PayMethod payMethod;
    private BigDecimal price;
    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;

    @Builder
    public CartResponse(Long id, Long productId, Long priceCategoryId, String name, int quantity, PayMethod payMethod, BigDecimal price, BigDecimal cardDiscount, BigDecimal phoneDiscount) {
        this.id = id;
        this.productId = productId;
        this.priceCategoryId = priceCategoryId;
        this.name = name;
        this.quantity = quantity;
        this.payMethod = payMethod;
        this.price = price;
        this.cardDiscount = cardDiscount;
        this.phoneDiscount = phoneDiscount;
    }

    public static CartResponse fromEntity(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .productId(cart.getProduct().getId())
                .priceCategoryId(cart.getPriceCategory().getId())
                .name(cart.getProduct().getName())
                .quantity(cart.getQuantity())
                .payMethod(cart.getPayMethod())
                .price(cart.getOriginalPrice())
                .cardDiscount(cart.getCardDiscount())
                .phoneDiscount(cart.getPhoneDiscount())
                .build();
    }

    public static Cart toEntity(CartResponse cartResponse, ProductReader productReader, PriceCategoryReader priceCategoryReader) {
        Product product = productReader.findById(cartResponse.getProductId()); // 적절한 예외 처리

        PriceCategory priceCategory = priceCategoryReader.findById(cartResponse.getPriceCategoryId()); // 적절한 예외 처리

        return Cart.builder()
                .id(cartResponse.getId())
                .quantity(cartResponse.getQuantity())
                .payMethod(cartResponse.getPayMethod())
                .product(product) // Product 객체
                .priceCategory(priceCategory) // PriceCategory 객체
                .build();
    }
}
