package pintoss.giftmall.domains.site_info.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;

public interface SiteInfoJpaRepository extends JpaRepository<SiteInfo, Long> {

}
