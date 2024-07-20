package pintoss.giftmall.domains.site_info.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.site_info.domain.Banner;
import pintoss.giftmall.domains.site_info.service.port.BannerRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BannerRepositoryImpl implements BannerRepository {

    private final BannerJpaRepository bannerJpaRepository;

    @Override
    public List<Banner> findAll() {
        return bannerJpaRepository.findAll();
    }

    @Override
    public Optional<Banner> findById(Long id) {
        return bannerJpaRepository.findById(id);
    }

    @Override
    public Banner register(Banner banner) {
        return bannerJpaRepository.save(banner);
    }

    @Override
    public Banner update(Banner banner) {
        return bannerJpaRepository.save(banner);
    }

    @Override
    public void deleteById(Long id) {
        bannerJpaRepository.deleteById(id);
    }

}
