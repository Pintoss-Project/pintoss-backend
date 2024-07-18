package pintoss.giftmall.domains.site_info.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class SiteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_id")
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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
