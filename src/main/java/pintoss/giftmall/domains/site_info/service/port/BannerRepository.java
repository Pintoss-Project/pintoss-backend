package pintoss.giftmall.domains.site_info.service.port;

import pintoss.giftmall.domains.site_info.domain.Banner;

import java.util.List;
import java.util.Optional;

public interface BannerRepository {

    List<Banner> findAll();

    Optional<Banner> findById(Long id);

    Banner register(Banner banner);

    Banner update(Banner banner);

    void deleteById(Long id);

}
