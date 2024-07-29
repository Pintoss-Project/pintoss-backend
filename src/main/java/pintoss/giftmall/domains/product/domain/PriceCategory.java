package pintoss.giftmall.domains.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PriceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_category_id")
    private Long id;

    @Column(length = 50)
    private String name;

    private int price;

    private int stock;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public PriceCategory(String name, int price, int stock, Product product) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.product = product;
    }

    public void updateStock(int stock) {
        this.stock = stock;
    }

}
