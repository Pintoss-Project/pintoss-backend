package pintoss.giftmall.domains.product.infra;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.domain.QProduct;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Product updateDiscount(Long id, BigDecimal discount, String type) {
        QProduct product = QProduct.product;
        BooleanExpression condition = product.id.eq(id);

        Product foundProduct = queryFactory.selectFrom(product)
                .where(condition)
                .fetchOne();

        if (foundProduct == null) {
            throw new IllegalArgumentException("상품권 정보를 찾을 수 없습니다.");
        }

        foundProduct.setDiscountPolicy(discount, type);

        return foundProduct;

    }

}
