package pintoss.giftmall.domains.product.infra;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.domain.QProduct;
import pintoss.giftmall.domains.product.dto.UpdateDiscountRequest;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long updateDiscount(Long id,  UpdateDiscountRequest discountRequest) {

        QProduct product = QProduct.product;
        Product foundProduct = queryFactory.selectFrom(product)
                .where(product.id.eq(id))
                .fetchOne();

        if (foundProduct == null) {
            throw new IllegalArgumentException("상품권 정보를 찾을 수 없습니다.");
        }

        if (discountRequest.getCardDiscount() != null) {
            foundProduct.setCardDiscount(discountRequest.getCardDiscount());
        }
        if (discountRequest.getPhoneDiscount() != null) {
            foundProduct.setPhoneDiscount(discountRequest.getPhoneDiscount());
        }

        return foundProduct.getId();

    }

}
