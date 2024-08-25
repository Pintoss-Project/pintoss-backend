package pintoss.giftmall.domains.site_info.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.common.enums.BannerImageCategory;
import pintoss.giftmall.domains.image.domain.Image;

@Entity
@Getter
@NoArgsConstructor
public class BannerImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id")
    private Banner banner;

    @Enumerated(EnumType.STRING)
    private BannerImageCategory category;

    @Builder
    public BannerImage(String url, Banner banner, BannerImageCategory category) {
        super(url);
        this.banner = banner;
        this.category = category;
    }

    public void updateUrl(String url) {
        if (url != null && !url.isEmpty()) {
            super.updateUrl(url);
        }
    }

}
