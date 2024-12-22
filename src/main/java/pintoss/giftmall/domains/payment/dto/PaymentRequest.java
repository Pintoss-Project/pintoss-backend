package pintoss.giftmall.domains.payment.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.common.enums.PayStatus;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.user.domain.User;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PaymentRequest {

    @NotBlank(message = "결제 상태는 필수 항목입니다.")
    private PayStatus payStatus;

    @NotBlank(message = "결제 수단은 필수 항목입니다.")
    private PayMethod payMethod;

    @Min(value = 1000, message = "결제 금액은 1000원 이상이어야 합니다.")
    private int payPrice;

    //거래 아이디
    private String transactionId;
    //가격 합계
    private BigDecimal amount;

    private Long userId;

    private Long orderId;
    private BigDecimal remainingAmount;

    @Builder
    public PaymentRequest(PayStatus payStatus, PayMethod payMethod, int payPrice,String transactionId,BigDecimal amount,Long userId,Long orderId,
                          BigDecimal remainingAmount) {
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.payPrice = payPrice;
        this.transactionId = transactionId;
        this.amount = amount;
        this.orderId = orderId;
        this.userId = userId;
        this.remainingAmount = remainingAmount;
    }

    public Payment toEntity(User user, Order order) {
        return Payment.builder()
                .payStatus(this.payStatus)
                .payMethod(this.payMethod)
                .amount(this.amount)
                .transactionId(this.transactionId)
                .order(order)
                .user(user)
                .build();
    }

}