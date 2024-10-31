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

    //인덱스 순으로 상품권 보여주기.
    @Query("SELECT p FROM Product  p order by p.index ASC")
    List<Product> findAllByIndexOrder();

    //상품권 엔티티에 있는 마지막 인덱스 조회
    @Query("SELECT COALESCE(MAX(p.index), 0) FROM Product p")
    int findMaxIndex();

}
