package pintoss.giftmall.domains.product.infra;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.domain.QProduct;
import pintoss.giftmall.domains.product.service.port.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return productJpaRepository.findByCategory(category);
    }

    @Override
    public List<Product> findByIsPopular(boolean isPopular) {
        if (isPopular) {
            return productJpaRepository.findByIsPopularTrue();
        } else {
            return productJpaRepository.findByIsPopularFalse();
        }
    }

    @Override
    public Product register(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }

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

        switch (type.toLowerCase()) {
            case "card":
                foundProduct.setCardDiscount(discount);
                break;
            case "phone":
                foundProduct.setPhoneDiscount(discount);
                break;
            default:
                throw new IllegalArgumentException("잘못된 타입입니다.");
        }

        return productJpaRepository.save(foundProduct);

    }

}
