package pintoss.giftmall.domains.site_info.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;

@Getter
@NoArgsConstructor
public class SiteInfoUpdateRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String tel;

    @NotBlank
    private String businessHour;

    @NotBlank
    private String address;

    @NotBlank
    private String owner;

    @NotBlank
    private String businesses;

    @NotBlank
    private String reportNumber;

    @Email
    private String email;

    private String kakao;
    private String openChat;

    @Builder
    public SiteInfoUpdateRequestDTO(String name, String tel, String businessHour, String address, String owner, String businesses, String reportNumber, String email, String kakao, String openChat) {
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
    }

    public SiteInfo toEntity() {
        return SiteInfo.builder()
                .name(this.name)
                .tel(this.tel)
                .businessHour(this.businessHour)
                .address(this.address)
                .owner(this.owner)
                .businesses(this.businesses)
                .reportNumber(this.reportNumber)
                .email(this.email)
                .kakao(this.kakao)
                .openChat(this.openChat)
                .build();
    }

}
