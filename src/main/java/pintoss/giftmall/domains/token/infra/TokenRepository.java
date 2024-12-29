package pintoss.giftmall.domains.token.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.token.domain.Token;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByAccessToken(String accessToken);
    Optional<Token> findByUsername(String username);

}


