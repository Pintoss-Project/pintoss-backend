package pintoss.giftmall.domains.site_info.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;
import pintoss.giftmall.domains.site_info.dto.SiteInfoUpdateRequest;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SiteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_id")
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 20)
    private String tel;

    @Column(length = 50)
    private String businessHour;

    @Column(length = 100)
    private String address;

    @Column(length = 20)
    private String owner;

    @Column(length = 20)
    private String businesses;

    @Column(length = 50)
    private String reportNumber;

    @Column(length = 254)
    private String email;

    @Column(length = 50)
    private String kakao;

    @Column(length = 50)
    private String openChat;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public SiteInfo(String name, String tel, String businessHour, String address, String owner, String businesses, String reportNumber, String email, String kakao, String openChat) {
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

    public void update(SiteInfoUpdateRequest requestDTO) {
        if (StringUtils.hasText(requestDTO.getName())) this.name = requestDTO.getName();
        if (StringUtils.hasText(requestDTO.getTel())) this.tel = requestDTO.getTel();
        if (StringUtils.hasText(requestDTO.getBusinessHour())) this.businessHour = requestDTO.getBusinessHour();
        if (StringUtils.hasText(requestDTO.getAddress())) this.address = requestDTO.getAddress();
        if (StringUtils.hasText(requestDTO.getOwner())) this.owner = requestDTO.getOwner();
        if (StringUtils.hasText(requestDTO.getBusinesses())) this.businesses = requestDTO.getBusinesses();
        if (StringUtils.hasText(requestDTO.getReportNumber())) this.reportNumber = requestDTO.getReportNumber();
        if (StringUtils.hasText(requestDTO.getEmail())) this.email = requestDTO.getEmail();
        if (StringUtils.hasText(requestDTO.getKakao())) this.kakao = requestDTO.getKakao();
        if (StringUtils.hasText(requestDTO.getOpenChat())) this.openChat = requestDTO.getOpenChat();
    }

}
