package pintoss.giftmall.domains.product.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.product.domain.PriceCategory;

import java.util.List;
import java.util.Optional;

public interface PriceCategoryRepository extends JpaRepository<PriceCategory, Long> {

    List<PriceCategory> findAllByProductId(Long productId);
    Optional<PriceCategory> findByIdAndProductId(Long id, Long productId);

}
