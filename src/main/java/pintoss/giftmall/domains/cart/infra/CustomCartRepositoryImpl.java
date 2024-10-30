package pintoss.giftmall.domains.cart.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import pintoss.giftmall.domains.board.infra.CustomBoardRepositoryImpl;
import pintoss.giftmall.domains.cart.domain.Cart;
import pintoss.giftmall.domains.cart.domain.QCart;
import pintoss.giftmall.domains.cart.dto.CartResponse;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.user.domain.User;

import java.util.Optional;

public class CustomCartRepositoryImpl implements CustomCartRepository {

    private final JPAQueryFactory queryFactory;

    public CustomCartRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<CartResponse> findByUserAndProductAndPriceCategory(User user, Product product, PriceCategory priceCategory) {
        QCart cart = QCart.cart;

        Cart foundCart = queryFactory
                .selectFrom(cart)
                .where(cart.user.eq(user)
                        .and(cart.product.eq(product))
                        .and(cart.priceCategory.eq(priceCategory)))
                .fetchOne();
        // Cart 엔티티를 CartResponse로 변환
        return Optional.ofNullable(foundCart).map(CartResponse::fromEntity);
    }
}
