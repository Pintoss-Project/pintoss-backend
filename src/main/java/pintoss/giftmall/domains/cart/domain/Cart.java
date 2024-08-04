package pintoss.giftmall.domains.cart.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @Setter
    private int quantity;
    private int price;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Cart(Product product, User user, int quantity, int price, PayMethod payMethod) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
        this.price = price;
        this.payMethod = payMethod;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

}
