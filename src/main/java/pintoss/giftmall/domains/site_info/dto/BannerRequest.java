package pintoss.giftmall.domains.site_info.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.site_info.domain.Banner;

@Getter
@NoArgsConstructor
public class BannerRequest {

    @NotBlank(message = "배너 제목은 필수 항목입니다.")
    private String bannerTitle;

    private String bannerLink;

    @NotBlank(message = "배너 데스크탑 이미지 URL은 필수 항목입니다.")
    private String desktopImageUrl;

    @NotBlank(message = "배너 모바일 이미지 URL은 필수 항목입니다.")
    private String mobileImageUrl;

    @Builder
    public BannerRequest(String bannerTitle, String bannerLink, String desktopImageUrl, String mobileImageUrl) {
        this.bannerTitle = bannerTitle;
        this.bannerLink = bannerLink;
        this.desktopImageUrl = desktopImageUrl;
        this.mobileImageUrl = mobileImageUrl;
    }

    public Banner toEntity() {
        return Banner.builder()
                .bannerTitle(this.bannerTitle)
                .bannerLink(this.bannerLink)
                .build();
    }

}
