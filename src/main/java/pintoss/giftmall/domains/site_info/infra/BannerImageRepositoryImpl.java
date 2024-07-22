package pintoss.giftmall.domains.site_info.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.site_info.domain.BannerImage;
import pintoss.giftmall.domains.site_info.service.port.BannerImageRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BannerImageRepositoryImpl implements BannerImageRepository {

    private final BannerImageJpaRepository bannerImageJpaRepository;

    @Override
    public void save(BannerImage bannerImage) {
        bannerImageJpaRepository.save(bannerImage);
    }

    @Override
    public List<BannerImage> findAllByBannerId(Long bannerId) {
        return bannerImageJpaRepository.findAllByBannerId(bannerId);
    }

    @Override
    public void delete(BannerImage bannerImage) {
        bannerImageJpaRepository.delete(bannerImage);
    }

    @Override
    public void saveAll(List<BannerImage> bannerImages) {
        bannerImageJpaRepository.saveAll(bannerImages);
    }

}
