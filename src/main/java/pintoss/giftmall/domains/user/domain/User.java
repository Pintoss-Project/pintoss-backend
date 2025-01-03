package pintoss.giftmall.domains.user.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pintoss.giftmall.common.enums.UserRole;
import pintoss.giftmall.domains.payment.domain.Payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Column(name = "user_uuid", unique = true, nullable = false, columnDefinition = "BINARY(50)")
    private UUID userId; // 추가된 UUID 필드

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

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

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    @PrePersist
    public void prePersist(){
        this.userId = UUID.randomUUID();
    }
}
