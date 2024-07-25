package pintoss.giftmall.domains.site_info.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.site_info.domain.Banner;

@Getter
@NoArgsConstructor
public class BannerRequest {

    @NotBlank
    private String bannerTitle;

    @NotBlank
    private String bannerLink;

    @Builder
    public BannerRequest(String bannerTitle, String bannerLink) {
        this.bannerTitle = bannerTitle;
        this.bannerLink = bannerLink;
    }

    public Banner toEntity() {
        return Banner.builder()
                .bannerTitle(this.bannerTitle)
                .bannerLink(this.bannerLink)
                .build();
    }

}
