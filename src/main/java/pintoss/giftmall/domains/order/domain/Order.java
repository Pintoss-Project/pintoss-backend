package pintoss.giftmall.domains.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
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

    @Column(length = 20)
    private String userName;

    @Column(length = 254)
    private String userEmail;

    @Column(length = 15)
    private String userPhone;

    @Column(length = 50)
    private String orderNo;

    private int orderPrice;

    @Column(length = 10)
    private String orderStatus;

    @Column(length = 10)
    private String payStatus;

    private boolean isSend;

    @Column(length = 10)
    private String payMethod;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

}
