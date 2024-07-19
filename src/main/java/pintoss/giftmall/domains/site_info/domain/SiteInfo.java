package pintoss.giftmall.domains.site_info.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "siteInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SiteInfoImage> images = new ArrayList<>();

}
