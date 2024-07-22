package pintoss.giftmall.domains.cart.infra;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.cart.domain.Cart;
import pintoss.giftmall.domains.cart.service.port.CartRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final CartJpaRepository cartJpaRepository;

    @Override
    public Cart save(Cart cart) {
        return cartJpaRepository.save(cart);
    }

    @Override
    public List<Cart> findByUserId(Long userId) {
        return cartJpaRepository.findByUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        cartJpaRepository.deleteById(id);
    }

    @Override
    public Cart updateCartItem(Long cartId, int quantity) {
        Cart cart = cartJpaRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);

        cart.setQuantity(quantity);

        return cartJpaRepository.save(cart);
    }
}
