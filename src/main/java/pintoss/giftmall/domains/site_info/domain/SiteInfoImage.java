package pintoss.giftmall.domains.site_info.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.common.enums.SiteInfoImageCategory;
import pintoss.giftmall.domains.image.domain.Image;

@Entity
@Getter
@NoArgsConstructor
public class SiteInfoImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private SiteInfo siteInfo;

    @Enumerated(EnumType.STRING)
    private SiteInfoImageCategory category;

    @Builder
    public SiteInfoImage(String url, SiteInfo siteInfo, SiteInfoImageCategory category) {
        super(url);
        this.siteInfo = siteInfo;
        this.category = category;
    }

}
