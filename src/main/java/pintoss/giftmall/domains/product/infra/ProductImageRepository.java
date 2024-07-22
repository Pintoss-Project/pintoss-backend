package pintoss.giftmall.domains.product.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.product.domain.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllByProductId(Long productId);

}
