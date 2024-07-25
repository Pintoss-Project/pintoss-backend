package pintoss.giftmall.domains.site_info.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private Long id;

    @Column(length = 100)
    private String bannerTitle;

    @Column(length = 100)
    private String bannerLink;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Banner(String bannerTitle, String bannerLink) {
        this.bannerTitle = bannerTitle;
        this.bannerLink = bannerLink;
    }

    public void update(String bannerTitle, String bannerLink) {
        if (bannerTitle != null) this.bannerTitle = bannerTitle;
        if (bannerLink != null) this.bannerLink = bannerLink;
    }

}
