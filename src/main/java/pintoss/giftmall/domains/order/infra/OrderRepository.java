package pintoss.giftmall.domains.order.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.order.domain.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

}
