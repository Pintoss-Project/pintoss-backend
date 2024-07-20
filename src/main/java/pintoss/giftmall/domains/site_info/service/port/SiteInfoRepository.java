package pintoss.giftmall.domains.site_info.service.port;

import pintoss.giftmall.domains.site_info.domain.SiteInfo;

import java.util.List;
import java.util.Optional;

public interface SiteInfoRepository {

    List<SiteInfo> findAll();

    SiteInfo update(SiteInfo siteInfo);

    Optional<SiteInfo> findById(Long id);

}
