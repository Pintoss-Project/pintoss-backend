package pintoss.giftmall.domains.payment.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.common.enums.PayStatus;
import pintoss.giftmall.domains.payment.domain.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PaymentResponse {

    private Long id;
    private PayStatus payStatus;
    private PayMethod payMethod;
    private BigDecimal amount;
    private BigDecimal remainingAmount;
    private String transactionId;
    private LocalDateTime approvedAt;

    @Builder
    public PaymentResponse(Long id, PayStatus payStatus, PayMethod payMethod, BigDecimal amount, int discountPrice, LocalDateTime approvedAt,
                           BigDecimal remainingAmount,
                           String transactionId) {
        this.id = id;
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.amount = amount;
        this.approvedAt = approvedAt;
        this.remainingAmount = remainingAmount;
        this.transactionId = transactionId;
    }

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .remainingAmount(payment.getRemainingAmount())
                .transactionId(payment.getTransactionId())
                .payStatus(payment.getPayStatus())
                .payMethod(payment.getPayMethod())
                .approvedAt(payment.getApprovedAt())
                .build();
    }

}