package pintoss.giftmall.domains.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="order_id")
    private Long id;

    private String userName;
    private String userEmail;
    private String userPhone;

    private String orderNo;
    private String orderPrice;
    private String orderStatus;
    private String payStatus;
    private boolean isSend;
    private String payMethod;

    @CreatedDate
    private LocalDateTime createdAt;

}
