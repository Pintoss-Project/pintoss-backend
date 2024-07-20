package pintoss.giftmall.domains.site_info.infra;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;
import pintoss.giftmall.domains.site_info.service.port.SiteInfoRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SiteInfoRepositoryImpl implements SiteInfoRepository {

    private final SiteInfoJpaRepository siteInfoJpaRepository;

    @Override
    public List<SiteInfo> findAll() {
        return siteInfoJpaRepository.findAll();
    }

    @Override
    public SiteInfo update(SiteInfo siteInfo) {
        return siteInfoJpaRepository.save(siteInfo);
    }

    @Override
    public Optional<SiteInfo> findById(Long id) {
        return siteInfoJpaRepository.findById(id);
    }

}
