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
    private int totalPrice;
    private int discountPrice;
    private LocalDateTime approvedAt;
    private Long orderId;

    @Builder
    public PaymentResponse(Long id, String payStatus, String payMethod, int totalPrice, int discountPrice, LocalDateTime approvedAt, Long orderId) {
        this.id = id;
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.approvedAt = approvedAt;
        this.orderId = orderId;
    }

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .payStatus(payment.getPayStatus())
                .payMethod(payment.getPayMethod())
                .totalPrice(payment.getTotalPrice())
                .discountPrice(payment.getDiscountPrice())
                .approvedAt(payment.getApprovedAt())
                .orderId(payment.getOrder().getId())
                .build();
    }

}