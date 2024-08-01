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

    @NotBlank(message = "배너 링크는 필수 항목입니다.")
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
