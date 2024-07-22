package pintoss.giftmall.domains.product.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.product.domain.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    List<Product> findByCategory(String category);

    List<Product> findByIsPopularTrue();

    List<Product> findByIsPopularFalse();

}
