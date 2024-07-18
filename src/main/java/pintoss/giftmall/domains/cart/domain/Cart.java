package pintoss.giftmall.domains.cart.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.user.domain.User;

@Entity
@Getter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    private int quantity;
    private int price;

    @Column(length = 20)
    private String checkoutMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
