package pintoss.giftmall.domains.site_info.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.site_info.domain.Banner;
import pintoss.giftmall.domains.site_info.domain.BannerImage;

@Getter
@NoArgsConstructor
public class BannerResponse {

    private Long id;
    private String bannerTitle;
    private String bannerLink;
    private String desktopImageUrl;
    private String mobileImageUrl;

    @Builder
    public BannerResponse(Long id, String bannerTitle, String bannerLink, String desktopImageUrl, String mobileImageUrl) {
        this.id = id;
        this.bannerTitle = bannerTitle;
        this.bannerLink = bannerLink;
        this.desktopImageUrl = desktopImageUrl;
        this.mobileImageUrl = mobileImageUrl;
    }

    public static BannerResponse fromEntity(Banner banner, String desktopImageUrl, String mobileImageUrl) {
        return BannerResponse.builder()
                .id(banner.getId())
                .bannerTitle(banner.getBannerTitle())
                .bannerLink(banner.getBannerLink())
                .desktopImageUrl(desktopImageUrl)
                .mobileImageUrl(mobileImageUrl)
                .build();
    }

}
