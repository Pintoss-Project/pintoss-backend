package pintoss.giftmall.domains.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import pintoss.giftmall.domains.order.domain.OrderProduct;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(length = 50)
    private String name;

    private boolean isPopular;
    private int cardDiscount;
    private int phoneDiscount;

    @Column(length = 100)
    private String homePage;

    @Column(length = 20)
    private String csCenter;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 30)
    private String publisher;

    @Column(length = 20)
    private String category;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceCategory> priceCategories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts;

}
