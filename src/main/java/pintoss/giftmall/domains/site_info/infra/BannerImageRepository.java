package pintoss.giftmall.domains.site_info.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.site_info.domain.BannerImage;

import java.util.List;

public interface BannerImageRepository extends JpaRepository<BannerImage, Long> {

    List<BannerImage> findAllByBannerId(Long bannerId);

}
