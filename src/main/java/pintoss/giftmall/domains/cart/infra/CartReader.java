package pintoss.giftmall.domains.cart.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.cart.domain.Cart;

@Component
@RequiredArgsConstructor
public class CartReader {

    private final CartRepository cartRepository;

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new NotFoundException("장바구니 아이템 id를 다시 확인해주세요."));
    }

}
