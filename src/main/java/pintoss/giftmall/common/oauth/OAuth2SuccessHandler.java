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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${FRONTEND_BASE_URL}")
    private String frontendBaseUrl;

    private final TokenProvider tokenProvider;

    public OAuth2SuccessHandler(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String accessToken = tokenProvider.generateAccessToken(authentication);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String name = principalDetails.getName();
        String email = principalDetails.getEmail();

        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
        String encodedAccessToken = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);

        Cookie nameCookie = new Cookie("name", encodedName);
        Cookie emailCookie = new Cookie("email", encodedEmail);
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
