package pintoss.giftmall.domains.site_info.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pintoss.giftmall.common.enums.SiteInfoImageCategory;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;
import pintoss.giftmall.domains.site_info.domain.SiteInfoImage;

import java.util.List;
import java.util.Optional;

public interface SiteInfoImageRepository extends JpaRepository<SiteInfoImage, Long> {

    Optional<SiteInfoImage> findBySiteInfoIdAndCategory(Long siteInfoId, SiteInfoImageCategory category);

    @Modifying
    @Query("DELETE FROM SiteInfoImage si WHERE si.siteInfo = :siteInfo")
    void deleteBySiteInfo(@Param("siteInfo") SiteInfo siteInfo);
}
