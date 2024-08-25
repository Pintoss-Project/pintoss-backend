package pintoss.giftmall.domains.site_info.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;

@Getter
@NoArgsConstructor
public class SiteInfoUpdateRequest {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    private String tel;

    @NotBlank(message = "영업 시간은 필수 항목입니다.")
    private String businessHour;

    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;

    @NotBlank(message = "소유자는 필수 항목입니다.")
    private String owner;

    @NotBlank(message = "업종은 필수 항목입니다.")
    private String businesses;

    @NotBlank(message = "신고 번호는 필수 항목입니다.")
    private String reportNumber;

    @Email(message = "유효한 이메일 주소를 입력하세요.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotBlank(message = "카카오톡 정보는 필수 항목입니다.")
    private String kakao;

    @NotBlank(message = "오픈 채팅 링크는 필수 항목입니다.")
    private String openChat;

    private String topImageUrl;
    private String bottomImageUrl;

    @Builder
    public SiteInfoUpdateRequest(String name, String tel, String businessHour, String address, String owner, String businesses, String reportNumber, String email, String kakao, String openChat, String topImageUrl, String bottomImageUrl) {
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
