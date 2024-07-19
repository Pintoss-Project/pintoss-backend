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
public class BannerImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id")
    private Banner banner;

    private String category;

    @Builder
    public BannerImage(String url, Banner banner, String category) {
        super(url);
        this.banner = banner;
        this.category = category;
    }

}
