package pintoss.giftmall.domains.site_info.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.common.enums.BannerImageCategory;
import pintoss.giftmall.domains.site_info.domain.Banner;
import pintoss.giftmall.domains.site_info.domain.BannerImage;

import java.util.List;
import java.util.Optional;

public interface BannerImageRepository extends JpaRepository<BannerImage, Long> {

    List<BannerImage> findAllByBannerId(Long bannerId);

    Optional<BannerImage> findByBanner(Banner banner);

    void deleteByBanner(Banner banner);

    Optional<BannerImage> findByBannerAndCategory(Banner banner, BannerImageCategory category);
}
