package pintoss.giftmall.domains.cart.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.cart.domain.Cart;
import pintoss.giftmall.domains.user.domain.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserId(Long userId);

    void deleteAllByUser(User user);

}
