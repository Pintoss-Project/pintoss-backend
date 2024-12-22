package pintoss.giftmall.domains.cart.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.cart.domain.Cart;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>, CustomCartRepository {

    List<Cart> findAllByUserId(Long userId);

    void deleteAllByUser(User user);

    //Optional<Cart> findByUserAndProductAndPriceCategory(User user, Product product, PriceCategory priceCategory);

}
