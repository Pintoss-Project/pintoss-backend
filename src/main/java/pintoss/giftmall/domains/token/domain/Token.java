package pintoss.giftmall.domains.token.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Builder
    public Token(String username, String accessToken, String refreshToken) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Token updateTokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        return this;
    }

    public void updateAccessToken(String newAccessToken) {
        this.accessToken = newAccessToken;
    }

}