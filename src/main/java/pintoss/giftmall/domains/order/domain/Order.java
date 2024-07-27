package pintoss.giftmall.domains.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="order_id")
    private Long id;

    @Column(length = 50)
    private String orderNo;

    private int orderPrice;

    @Column(length = 10)
    private String orderStatus;

    private int discountPrice;

    @Column(length = 10)
    private String payMethod;

    @Column(nullable = false)
    private boolean isSent = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Order(String orderNo, int orderPrice, String orderStatus, int discountPrice, boolean isSent, String payMethod, User user) {
        this.orderNo = orderNo;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.discountPrice = discountPrice;
        this.payMethod = payMethod;
        this.isSent = isSent;
        this.user = user;
    }

}
