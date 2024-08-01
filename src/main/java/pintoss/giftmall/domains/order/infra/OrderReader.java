package pintoss.giftmall.domains.order.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.order.domain.Order;

@Component
@RequiredArgsConstructor
public class OrderReader {

    private final OrderRepository orderRepository;

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("주문 id를 다시 확인해주세요."));
    }

}
