package pintoss.giftmall.domains.payment.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDateTime;

@Getter
public class PaymentResponse {

    private Long id;
    private String payStatus;
    private String payMethod;
    private int totalPrice;
    private int discountPrice;
    private LocalDateTime approvedAt;

    @Builder
    public PaymentResponse(Long id, String payStatus, String payMethod, int totalPrice, int discountPrice, LocalDateTime approvedAt) {
        this.id = id;
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.approvedAt = approvedAt;
    }

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .payStatus(payment.getPayStatus())
                .payMethod(payment.getPayMethod())
                .totalPrice(payment.getTotalPrice())
                .discountPrice(payment.getDiscountPrice())
                .approvedAt(payment.getApprovedAt())
                .build();
    }

}