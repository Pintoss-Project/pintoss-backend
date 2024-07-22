package pintoss.giftmall.domains.site_info.service.port;

import pintoss.giftmall.domains.site_info.domain.SiteInfoImage;

import java.util.List;

public interface SiteInfoImageRepository {

    void save(SiteInfoImage siteInfoImage);

    List<SiteInfoImage> findAllBySiteId(Long siteId);

    void delete(SiteInfoImage siteInfoImage);

    void saveAll(List<SiteInfoImage> siteInfoImages);

}
