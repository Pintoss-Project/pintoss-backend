package pintoss.giftmall.domains.payment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private int payPrice;

    private BigDecimal amount;

    private String transactionId;

    @CreatedDate
    private LocalDateTime approvedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Payment(PayStatus payStatus, PayMethod payMethod, int payPrice,String transactionId,BigDecimal amount, User user, Order order) {
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.payPrice = payPrice;
        this.transactionId = transactionId;
        this.amount = amount;
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

}
