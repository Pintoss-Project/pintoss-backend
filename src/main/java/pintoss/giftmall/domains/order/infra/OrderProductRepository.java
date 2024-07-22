package pintoss.giftmall.domains.order.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.order.domain.OrderProduct;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> findByOrderId(Long orderId);

    List<OrderProduct> findByProductId(Long productId);

}
