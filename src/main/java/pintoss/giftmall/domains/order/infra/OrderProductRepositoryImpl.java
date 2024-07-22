package pintoss.giftmall.domains.order.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.order.domain.OrderProduct;
import pintoss.giftmall.domains.order.service.port.OrderProductRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepository {

    private final OrderProductJpaRepository orderProductJpaRepository;

    @Override
    public List<OrderProduct> findByOrderId(Long orderId) {
        return orderProductJpaRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderProduct> findByProductId(Long productId) {
        return orderProductJpaRepository.findByProductId(productId);
    }

    @Override
    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductJpaRepository.save(orderProduct);
    }

}
