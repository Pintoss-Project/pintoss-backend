package pintoss.giftmall.domains.payment.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.user.domain.User;

@Getter
@NoArgsConstructor
public class PaymentRequest {

    @NotBlank(message = "결제 상태는 필수 항목입니다.")
    private String payStatus;

    @NotBlank(message = "결제 수단은 필수 항목입니다.")
    private String payMethod;

    @Min(value = 1000, message = "결제 금액은 1000원 이상이어야 합니다.")
    private int payPrice;

    @Builder
    public PaymentRequest(String payStatus, String payMethod, int payPrice) {
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.payPrice = payPrice;
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