package pintoss.giftmall.domains.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pintoss.giftmall.common.enums.OrderStatus;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.common.enums.PayStatus;
import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDateTime;

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
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int discountPrice;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @Column(nullable = false)
    private boolean isSent = false;

    @Column(nullable = false)
    private boolean isOrderInCart = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Order(String orderNo, int orderPrice, OrderStatus orderStatus, int discountPrice, boolean isSent, boolean isOrderInCart, PayMethod payMethod, User user) {
        this.orderNo = orderNo;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.discountPrice = discountPrice;
        this.payMethod = payMethod;
        this.isSent = isSent;
        this.isOrderInCart = isOrderInCart;
        this.user = user;
    }

    public void updatePayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

}
