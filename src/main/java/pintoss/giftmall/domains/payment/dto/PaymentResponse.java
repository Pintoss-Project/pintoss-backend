package pintoss.giftmall.domains.payment.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.domains.payment.domain.Payment;

import java.time.LocalDateTime;

@Getter
public class PaymentResponse {

    private Long id;
    private String payStatus;
    private String payMethod;
    private int payPrice;
    private LocalDateTime approvedAt;

    @Builder
    public PaymentResponse(Long id, String payStatus, String payMethod, int payPrice, int discountPrice, LocalDateTime approvedAt) {
        this.id = id;
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.payPrice = payPrice;
        this.approvedAt = approvedAt;
    }

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .payStatus(payment.getPayStatus())
                .payMethod(payment.getPayMethod())
                .payPrice(payment.getPayPrice())
                .approvedAt(payment.getApprovedAt())
                .build();
    }

}