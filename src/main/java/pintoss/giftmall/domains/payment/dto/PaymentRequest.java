package pintoss.giftmall.domains.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.dto.OrderRequest;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.user.domain.User;

@Getter
@NoArgsConstructor
public class PaymentRequest {

    private String payStatus;
    private String payMethod;
    private int totalPrice;
    private int discountPrice;

    @Builder
    public PaymentRequest(String payStatus, String payMethod, int totalPrice, int discountPrice) {
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
    }

    public Payment toEntity(Order order, User user) {
        return Payment.builder()
                .payStatus(this.payStatus)
                .payMethod(this.payMethod)
                .totalPrice(this.totalPrice)
                .discountPrice(this.discountPrice)
                .order(order)
                .user(user)
                .build();
    }

    public OrderRequest toOrderRequest() {
        return OrderRequest.builder()
                .orderPrice(this.totalPrice)
                .payMethod(this.payMethod)
                .build();
    }

}