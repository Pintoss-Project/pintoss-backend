package pintoss.giftmall.domains.cart.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.cart.domain.Cart;

@Component
@RequiredArgsConstructor
public class CartRepositoryReader {

    private final CartRepository cartRepository;

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "장바구니 아이템 id를 다시 확인해주세요."));
    }

}
