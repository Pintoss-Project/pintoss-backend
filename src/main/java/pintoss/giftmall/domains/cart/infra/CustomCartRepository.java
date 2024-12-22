package pintoss.giftmall.domains.cart.infra;

import pintoss.giftmall.domains.cart.dto.CartResponse;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.user.domain.User;

import java.util.Optional;

public interface CustomCartRepository {

    Optional<CartResponse> findByUserAndProductAndPriceCategory(User user, Product product, PriceCategory priceCategory);
}
