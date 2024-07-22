package pintoss.giftmall.domains.product.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.product.domain.PriceCategory;

public interface PriceCategoryJpaRepository extends JpaRepository<PriceCategory, Long> {
}
