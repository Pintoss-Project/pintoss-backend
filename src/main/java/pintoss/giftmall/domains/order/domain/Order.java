package pintoss.giftmall.domains.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
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

    @Column(length = 10)
    private String payMethod;

    @Column(nullable = false)
    private boolean isSent = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Order(String orderNo, int orderPrice, String orderStatus, boolean isSent, String payMethod, User user, Payment payment) {
        this.orderNo = orderNo;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.payMethod = payMethod;
        this.isSent = isSent;
        this.user = user;
        this.payment = payment;
    }

    public void assignPayment(Payment payment) {
        this.payment = payment;
    }

}
