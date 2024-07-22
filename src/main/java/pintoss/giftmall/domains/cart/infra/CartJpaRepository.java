package pintoss.giftmall.domains.cart.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.cart.domain.Cart;

import java.util.List;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);

}