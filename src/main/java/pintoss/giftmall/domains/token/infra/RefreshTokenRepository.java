package pintoss.giftmall.domains.token.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.token.domain.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String refreshToken);
    Optional<RefreshToken> findByUserId(UUID userId);
    boolean existsByToken(String refreshToken);
    boolean existsByUserId(UUID userId);
}
