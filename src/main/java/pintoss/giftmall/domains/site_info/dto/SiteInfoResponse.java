package pintoss.giftmall.domains.site_info.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class SiteInfoResponse {

    private Long id;
    private String name;
    private String tel;
    private String businessHour;
    private String address;
    private String owner;
    private String businesses;
    private String reportNumber;
    private String email;
    private String kakao;
    private String openChat;
    private String topImageUrl;
    private String bottomImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public SiteInfoResponse(Long id, String name, String tel, String businessHour, String address, String owner, String businesses, String reportNumber, String email, String kakao, String openChat, String topImageUrl, String bottomImageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.businessHour = businessHour;
        this.address = address;
        this.owner = owner;
        this.businesses = businesses;
        this.reportNumber = reportNumber;
        this.email = email;
        this.kakao = kakao;
        this.openChat = openChat;
        this.topImageUrl = topImageUrl;
        this.bottomImageUrl = bottomImageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static SiteInfoResponse fromEntity(SiteInfo siteInfo, String topImageUrl, String bottomImageUrl) {
        return SiteInfoResponse.builder()
                .id(siteInfo.getId())
                .name(siteInfo.getName())
                .tel(siteInfo.getTel())
                .businessHour(siteInfo.getBusinessHour())
                .address(siteInfo.getAddress())
                .owner(siteInfo.getOwner())
                .businesses(siteInfo.getBusinesses())
                .reportNumber(siteInfo.getReportNumber())
                .email(siteInfo.getEmail())
                .kakao(siteInfo.getKakao())
                .openChat(siteInfo.getOpenChat())
                .topImageUrl(topImageUrl)
                .bottomImageUrl(bottomImageUrl)
                .createdAt(siteInfo.getCreatedAt())
                .updatedAt(siteInfo.getUpdatedAt())
                .build();
    }

}
