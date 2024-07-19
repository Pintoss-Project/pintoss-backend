package pintoss.giftmall.domains.site_info.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.image.domain.Image;

@Entity
@Getter
@NoArgsConstructor
public class SiteInfoImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private SiteInfo siteInfo;

    private String category;

    @Builder
    public SiteInfoImage(String url, SiteInfo siteInfo, String category) {
        super(url);
        this.siteInfo = siteInfo;
        this.category = category;
    }

}
