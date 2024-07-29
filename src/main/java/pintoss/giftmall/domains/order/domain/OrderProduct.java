package pintoss.giftmall.domains.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.product.domain.PriceCategory;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private PriceCategory priceCategory;

    @Builder
    public OrderProduct(int quantity, int price, Order order, Product product, PriceCategory priceCategory) {
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.product = product;
        this.priceCategory = priceCategory;
    }

    public void updateOrder(Order order) {
        this.order = order;
    }

}
