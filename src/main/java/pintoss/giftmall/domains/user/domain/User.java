package pintoss.giftmall.domains.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pintoss.giftmall.common.enums.UserRole;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 254)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 20)
    private String name;

    @Column(length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isActive = true;

    private String inflow;

    private boolean isNaverConnected = false;
    private boolean isKakaoConnected = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public User(String email, String password, String name, String phone, UserRole role, String inflow) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role != null ? role : UserRole.USER;
        this.isActive = true;
        this.inflow = inflow;
    }

    public void deactivate() {
        this.isActive = false;
        this.email = "deactivated_" + this.id + "@user.com";
        this.name = "탈퇴 사용자";
        this.phone = "000-0000-0000";
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public void connectNaver() {
        this.isNaverConnected = true;
    }

    public void connectKakao() {
        this.isKakaoConnected = true;
    }

}
