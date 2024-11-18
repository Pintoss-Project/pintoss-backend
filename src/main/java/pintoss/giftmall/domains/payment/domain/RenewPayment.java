package pintoss.giftmall.domains.payment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.common.enums.PayStatus;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.user.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RenewPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String serviceId;

    @Column(length = 50, nullable = false)
    private String orderId;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(length = 50, unique = true, nullable = false)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    private BigDecimal payPrice;

    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @CreatedDate
    private LocalDateTime approvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
