package pintoss.giftmall.domains.site_info.service.port;

import pintoss.giftmall.domains.site_info.domain.BannerImage;

import java.util.List;

public interface BannerImageRepository {

    void save(BannerImage bannerImage);

    List<BannerImage> findAllByBannerId(Long bannerId);

    void delete(BannerImage bannerImage);

    void saveAll(List<BannerImage> bannerImages);

}
