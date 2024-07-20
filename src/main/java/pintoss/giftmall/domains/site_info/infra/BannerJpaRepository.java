package pintoss.giftmall.domains.site_info.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.site_info.domain.Banner;

public interface BannerJpaRepository extends JpaRepository<Banner, Long> {
}
