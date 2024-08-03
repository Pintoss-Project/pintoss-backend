package pintoss.giftmall.domains.token.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.common.exceptions.TokenException;
import pintoss.giftmall.domains.token.domain.Token;
import pintoss.giftmall.domains.token.infra.TokenRepository;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveOrUpdate(String username, String refreshToken, String accessToken) {
        Token token = tokenRepository.findByUsername(username)
                .map(existingToken -> {
                    existingToken.updateTokens(accessToken, refreshToken);
                    return existingToken;
                })
                .orElse(new Token(accessToken, refreshToken));
        tokenRepository.save(token);
    }

    public Token findByAccessTokenOrThrow(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new TokenException(ErrorCode.INVALID_TOKEN));
    }

    public void updateToken(String newAccessToken, Token token) {
        token.updateAccessToken(newAccessToken);
        tokenRepository.save(token);
    }

    public String findRefreshTokenByUsernameOrThrow(String username) {
        return tokenRepository.findByUsername(username)
                .map(Token::getRefreshToken)
                .orElseThrow(() -> new TokenException(ErrorCode.INVALID_TOKEN));
    }

}
