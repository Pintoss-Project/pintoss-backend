package pintoss.giftmall.domains.order.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.common.enums.OrderStatus;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.user.domain.User;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotBlank(message = "주문 번호는 필수 항목입니다.")
    private String orderNo;

    @Min(value = 1000, message = "주문 가격은 1000원 이상이어야 합니다.")
    private int orderPrice;

    @NotBlank(message = "주문 상태는 필수 항목입니다.")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotBlank(message = "결제 수단은 필수 항목입니다.")
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @NotNull(message = "발송 여부는 필수 항목입니다.")
    private boolean isSent = false;

    @NotNull(message = "장바구니 주문 여부는 필수 항목입니다.")
    private boolean isOrderInCart = false;

    @NotNull(message = "주문 상품 목록은 필수 항목입니다.")
    @Size(min = 1, message = "주문 상품은 하나 이상이어야 합니다.")
    private List<OrderProductRequest> orderProducts;

    @Builder
    public OrderRequest(String orderNo, int orderPrice, OrderStatus orderStatus, boolean isSent, boolean isOrderInCart, PayMethod payMethod, List<OrderProductRequest> orderProducts) {
        this.orderNo = orderNo;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.payMethod = payMethod;
        this.isSent = isSent;
        this.isOrderInCart = isOrderInCart;
        this.orderProducts = orderProducts;
    }

    public Order toEntity(User user) {
        return Order.builder()
                .orderNo(orderNo)
                .orderPrice(orderPrice)
                .orderStatus(orderStatus)
                .payMethod(payMethod)
                .isSent(isSent)
                .isOrderInCart(isOrderInCart)
                .user(user)
                .build();
    }

}
