package pintoss.giftmall.domains.site_info.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.site_info.domain.SiteInfoImage;
import pintoss.giftmall.domains.site_info.service.port.SiteInfoImageRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SiteInfoImageRepositoryImpl implements SiteInfoImageRepository {

    private final SiteInfoImageJpaRepository siteInfoImageJpaRepository;

    @Override
    public void save(SiteInfoImage siteInfoImage) {
        siteInfoImageJpaRepository.save(siteInfoImage);
    }

    @Override
    public List<SiteInfoImage> findAllBySiteId(Long siteId) {
        return siteInfoImageJpaRepository.findAllBySiteId(siteId);
    }

    @Override
    public void delete(SiteInfoImage siteInfoImage) {
        siteInfoImageJpaRepository.delete(siteInfoImage);
    }

    @Override
    public void saveAll(List<SiteInfoImage> siteInfoImages) {
        siteInfoImageJpaRepository.saveAll(siteInfoImages);
    }

}
