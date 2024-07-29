package pintoss.giftmall.domains.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.user.domain.User;

@Getter
@NoArgsConstructor
public class PaymentRequest {

    private String payStatus;
    private String payMethod;
    private int payPrice;
    private int discountPrice;

    @Builder
    public PaymentRequest(String payStatus, String payMethod, int payPrice, int discountPrice) {
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.payPrice = payPrice;
        this.discountPrice = discountPrice;
    }

    public Payment toEntity(User user, Order order) {
        return Payment.builder()
                .payStatus(this.payStatus)
                .payMethod(this.payMethod)
                .payPrice(this.payPrice)
                .order(order)
                .user(user)
                .build();
    }

}