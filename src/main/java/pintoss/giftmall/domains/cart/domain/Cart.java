package pintoss.giftmall.domains.cart.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.user.domain.User;

import java.math.BigDecimal;
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

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_category_id")
    private PriceCategory priceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Cart(Long id, Product product, PriceCategory priceCategory, User user, int quantity, PayMethod payMethod) {
        this.id = id;
        this.product = product;
        this.priceCategory = priceCategory;
        this.user = user;
        this.quantity = quantity;
        this.payMethod = payMethod;
        this.cardDiscount = product.getCardDiscount();
        this.phoneDiscount = product.getPhoneDiscount();
    }
    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updatePayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
    }

    public BigDecimal getOriginalPrice() {
        return BigDecimal.valueOf(priceCategory.getPrice());
    }

}
