package pintoss.giftmall.common.oauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pintoss.giftmall.common.enums.UserRole;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.common.exceptions.TokenException;
import pintoss.giftmall.domains.token.domain.Token;
import pintoss.giftmall.domains.token.service.TokenService;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private String key = "7GpDbii7H429gsoxV/eRApcirwTTSKa7zKSlFtpqBeqYoW+u3bZ9a+NqkowZQ74S+myXcGiQl0Wawgw1o68nqA==";
    private SecretKey secretKey;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;
    private static final String KEY_ROLE = "role";

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @PostConstruct
    private void setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(Authentication authentication, String accessToken) {
        String refreshToken = generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME);
        tokenService.saveOrUpdate(authentication.getName(), refreshToken, accessToken);

        return refreshToken;
    }

    private String generateToken(Authentication authentication, long expireTime) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        String email;

        Object principal = authentication.getPrincipal();
        log.info("result:::"+principal);

        if (principal instanceof PrincipalDetails) {
            email = ((PrincipalDetails) principal).getUsername();
        } else if (principal instanceof String) {
            email = (String) principal;
        } else {
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
        }
        log.info("userEmail:::"+email);
        return Jwts.builder()
                .setSubject(email)
                .claim(KEY_ROLE, authorities)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        log.info("parsingValue::"+claims.getSubject());
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

        User user = userRepository.findByEmail(claims.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        log.info("result::"+user);
        // attributes를 빈 Map으로 초기화 (필요에 따라 적절한 값으로 설정)
        Map<String, Object> attributes = new HashMap<>();

        PrincipalDetails principalDetails = new PrincipalDetails(user, attributes, null);
        return new UsernamePasswordAuthenticationToken(principalDetails, token, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String authoritiesClaim = (String) claims.get("auth");
        if (authoritiesClaim == null || authoritiesClaim.isEmpty()) {
            return List.of(new SimpleGrantedAuthority(UserRole.USER.toString()));
        }
        return List.of(authoritiesClaim.split(",")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String reissueAccessToken(String accessToken) {
        if (StringUtils.hasText(accessToken)) {
            Token token = tokenService.findByAccessTokenOrThrow(accessToken);
            String refreshToken = token.getRefreshToken();

            if (validateToken(refreshToken)) {
                String reissueAccessToken = generateAccessToken(getAuthentication(refreshToken));
                tokenService.updateToken(reissueAccessToken, token);
                return reissueAccessToken;
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        Claims claims = parseClaims(token);
        return claims.getExpiration().after(new Date());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (MalformedJwtException e) {
            throw new TokenException(ErrorCode.INVALID_TOKEN);
        } catch (SecurityException e) {
            throw new TokenException(ErrorCode.INVALID_JWT_SIGNATURE);
        }
    }

}
