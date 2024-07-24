package pintoss.giftmall.domains.site_info.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    public void update(String name, String tel, String businessHour, String address, String owner, String businesses, String reportNumber, String email, String kakao, String openChat) {
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

}
