package pintoss.giftmall.domains.cart.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import pintoss.giftmall.config.QueryDslConfig;
import pintoss.giftmall.domains.cart.dto.CartResponse;
import pintoss.giftmall.domains.cart.infra.CartRepository;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.infra.PriceCategoryReader;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.util.Optional;

@Disabled
@DataJpaTest
@Import(QueryDslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PriceCategoryRepository priceCategoryRepository;
    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;
    
    @BeforeEach
    void createTest() {
        queryFactory = new JPAQueryFactory(em);
    }
    
    @Test
    @DisplayName("카테고리 회원별 조회")
    public void findCartTest() {
        User user = userRepository.findById(1L).get();
        Product product = productRepository.findById(1L).get();
        PriceCategory priceCategory = priceCategoryRepository.findById(1L).get();

        Optional<CartResponse> cartResponse = cartRepository
                .findByUserAndProductAndPriceCategory(user,product,priceCategory);

        System.out.println(cartResponse.get());
    }
}
