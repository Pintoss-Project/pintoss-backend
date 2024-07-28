package pintoss.giftmall.domains.order.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.user.domain.User;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotBlank
    private String orderNo;

    @NotNull
    private int orderPrice;

    @NotNull
    private int discountPrice;

    @NotBlank
    private String orderStatus;

    @NotBlank
    private String payMethod;

    @NotNull
    private boolean isSent = false;

    @NotNull
    private boolean isCart = false;

    @Builder
    public OrderRequest(String orderNo, int orderPrice, String orderStatus, boolean isSent, boolean isCart, String payMethod) {
        this.orderNo = orderNo;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.payMethod = payMethod;
        this.isSent = isSent;
        this.isCart = isCart;
    }

    public Order toEntity(User user) {
        return Order.builder()
                .orderNo(orderNo)
                .orderPrice(orderPrice)
                .orderStatus(orderStatus)
                .payMethod(payMethod)
                .isSent(isSent)
                .isCart(isCart)
                .user(user)
                .build();
    }

    public Payment toPaymentEntity(User user, Order order) {
        return Payment.builder()
                .payStatus(this.orderStatus)
                .payMethod(this.payMethod)
                .payPrice(this.orderPrice)
                .order(order)
                .user(user)
                .build();
    }

}
