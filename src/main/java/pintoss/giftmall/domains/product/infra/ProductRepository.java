package pintoss.giftmall.domains.product.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pintoss.giftmall.common.enums.ProductCategory;
import pintoss.giftmall.domains.product.domain.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    List<Product> findByCategory(ProductCategory category);

    @Query("SELECT p FROM Product p WHERE p.isPopular = true")
    List<Product> findByIsPopularTrue();

    @Query("SELECT p FROM Product p WHERE p.isPopular = false")
    List<Product> findByIsPopularFalse();

}
