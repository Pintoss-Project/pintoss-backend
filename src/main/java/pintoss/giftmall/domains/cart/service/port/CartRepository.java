package pintoss.giftmall.domains.cart.service.port;

import pintoss.giftmall.domains.cart.domain.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    Cart save(Cart cart);
    List<Cart> findByUserId(Long userId);

    void deleteById(Long id);

    Cart updateCartItem(Long cartId, int quantity);

}
