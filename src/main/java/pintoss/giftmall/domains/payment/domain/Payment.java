package pintoss.giftmall.domains.payment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(length = 10)
    private String payStatus;

    @Column(length = 10)
    private String payMethod;

    private int totalPrice;
    private int discountPrice;

    @CreatedDate
    private LocalDateTime approvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Payment(String payStatus, String payMethod, int totalPrice, int discountPrice, User user) {
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.user = user;
    }

    public void refund() {
        this.payStatus = "refunded";
    }

}
