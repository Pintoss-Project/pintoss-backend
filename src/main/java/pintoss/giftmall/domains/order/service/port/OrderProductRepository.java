package pintoss.giftmall.domains.order.service.port;

import pintoss.giftmall.domains.order.domain.OrderProduct;

import java.util.List;

public interface OrderProductRepository {

    List<OrderProduct> findByOrderId(Long orderId);

    List<OrderProduct> findByProductId(Long productId);

    OrderProduct save(OrderProduct orderProduct);

}
