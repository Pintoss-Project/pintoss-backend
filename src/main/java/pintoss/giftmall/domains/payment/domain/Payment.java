package pintoss.giftmall.domains.payment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.common.enums.PayStatus;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal remainingAmount; // 남은 결제 금액

    @Column
    private String transactionId;

    @Column(nullable = true)
    private String payMessage;

    @Column(length = 255)
    private String returnUrl;

    @CreatedDate
    private LocalDateTime approvedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Payment(PayStatus payStatus, PayMethod payMethod, String transactionId, BigDecimal amount, BigDecimal remainingAmount, String returnUrl, String payMessage, User user, Order order) {
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.transactionId = transactionId;
        this.amount = amount;
        this.remainingAmount = amount;
        this.returnUrl = returnUrl;
        this.payMessage = payMessage;
        this.user = user;
        this.order = order;
    }

    public void completePayment() {
        this.payStatus = PayStatus.COMPLETED;
    }

    public void failPayment() {
        this.payStatus = PayStatus.FAILED;
    }

    public void refund() {
        this.payStatus = PayStatus.REFUNDED;
    }

    // 부분 취소 후 남은 금액 업데이트
    public void updateAmountAfterPartialCancel(BigDecimal cancelAmount) {
        if (this.remainingAmount.compareTo(cancelAmount) < 0) {
            throw new IllegalArgumentException("취소 금액이 남은 금액보다 클 수 없습니다.");
        }
        this.remainingAmount = this.remainingAmount.subtract(cancelAmount);

        // 전체 취소일 경우 상태를 변경
        if (this.remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
            this.payStatus = PayStatus.CANCELLED;
        }
    }
}
