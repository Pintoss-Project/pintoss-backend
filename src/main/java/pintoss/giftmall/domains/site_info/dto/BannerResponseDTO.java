package pintoss.giftmall.domains.site_info.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.site_info.domain.Banner;

@Getter
@NoArgsConstructor
public class BannerResponseDTO {

    private Long id;
    private String bannerTitle;
    private String bannerLink;

    @Builder
    public BannerResponseDTO(Long id, String bannerTitle, String bannerLink) {
        this.id = id;
        this.bannerTitle = bannerTitle;
        this.bannerLink = bannerLink;
    }

    public static BannerResponseDTO fromEntity(Banner banner) {
        return BannerResponseDTO.builder()
                .id(banner.getId())
                .bannerTitle(banner.getBannerTitle())
                .bannerLink(banner.getBannerLink())
                .build();
    }


}
