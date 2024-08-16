package pintoss.giftmall.common.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${FRONTEND_BASE_URL}")
    private String frontendBaseUrl;

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public OAuth2SuccessHandler(TokenProvider tokenProvider, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getName() != null && !user.getName().isEmpty() &&
                    user.getPhone() != null && !user.getPhone().isEmpty()) {

                String accessToken = tokenProvider.generateAccessToken(authentication);
                String encodedAccessToken = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);

                Cookie accessTokenCookie = new Cookie("accessToken", encodedAccessToken);
                accessTokenCookie.setHttpOnly(true);
                accessTokenCookie.setPath("/");
                accessTokenCookie.setMaxAge(30 * 60);
                response.addCookie(accessTokenCookie);

                response.sendRedirect(frontendBaseUrl);
                return;
            }
        }

        String accessToken = tokenProvider.generateAccessToken(authentication);
        String encodedAccessToken = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);

        Cookie nameCookie = new Cookie("name", URLEncoder.encode(principalDetails.getName(), StandardCharsets.UTF_8));
        Cookie emailCookie = new Cookie("email", URLEncoder.encode(email, StandardCharsets.UTF_8));
        Cookie accessTokenCookie = new Cookie("accessToken", encodedAccessToken);

        nameCookie.setHttpOnly(true);
        emailCookie.setHttpOnly(true);
        accessTokenCookie.setHttpOnly(true);

        nameCookie.setPath("/");
        emailCookie.setPath("/");
        accessTokenCookie.setPath("/");

        nameCookie.setMaxAge(30 * 60);
        emailCookie.setMaxAge(30 * 60);
        accessTokenCookie.setMaxAge(30 * 60);

        response.addCookie(nameCookie);
        response.addCookie(emailCookie);
        response.addCookie(accessTokenCookie);

        response.sendRedirect(frontendBaseUrl + "/register?oauth=true");
    }

}
