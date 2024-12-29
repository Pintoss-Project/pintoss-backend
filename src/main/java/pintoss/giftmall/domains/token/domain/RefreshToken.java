package pintoss.giftmall.domains.token.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_tokens_id")
    private Long id;

    @Column(name = "users_uuid", columnDefinition = "BINARY(50)")
    private UUID userId;

    @Column(name = "token", nullable = false)
    private String token;
    
    //토큰 업데이트
    public RefreshToken updateToken(String newToken) {
        this.token = newToken;
        return this;
    }

}