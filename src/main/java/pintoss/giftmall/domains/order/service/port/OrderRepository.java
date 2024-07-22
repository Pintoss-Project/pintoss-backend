package pintoss.giftmall.domains.order.service.port;

import pintoss.giftmall.domains.order.domain.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> findAll();

    List<Order> findByUserId(Long userId);

}