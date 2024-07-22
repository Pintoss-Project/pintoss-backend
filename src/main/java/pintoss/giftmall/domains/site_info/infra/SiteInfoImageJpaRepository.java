package pintoss.giftmall.domains.site_info.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.site_info.domain.SiteInfoImage;

import java.util.List;

public interface SiteInfoImageJpaRepository extends JpaRepository<SiteInfoImage, Long> {

    List<SiteInfoImage> findAllBySiteId(Long siteId);

}