package pintoss.giftmall.domains.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.product.domain.Product;

@Entity
@Getter
@NoArgsConstructor
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    private int quantity;
    private int price;
    private Long priceCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public OrderProduct(Long priceCategoryId, int quantity, int price, Order order, Product product) {
        this.priceCategoryId = priceCategoryId;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.product = product;
    }

    public void updateOrder(Order order) {
        this.order = order;
    }

}
