package pintoss.giftmall.domains.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.payment.domain.Payment;

@Getter
@NoArgsConstructor
public class PaymentRequest {

    private String payStatus;
    private String payMethod;
    private int totalPrice;
    private int discountPrice;
    private Long orderId;

    @Builder
    public PaymentRequest(String payStatus, String payMethod, int totalPrice, int discountPrice, Long orderId) {
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.orderId = orderId;
    }

    public Payment toEntity(Order order) {
        return Payment.builder()
                .payStatus(this.payStatus)
                .payMethod(this.payMethod)
                .totalPrice(this.totalPrice)
                .discountPrice(this.discountPrice)
                .order(order)
                .build();
    }
}