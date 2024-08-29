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
import pintoss.giftmall.common.enums.UserRole;
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

        System.out.println("principalDetails : " + principalDetails);

        if (email == null || email.isEmpty()) {
            throw new ServletException("Email information is missing after OAuth login.");
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {

            User newUser = User.builder()
                    .email(email)
                    .role(UserRole.USER)
                    .build();
            userRepository.save(newUser);

            Cookie emailCookie = new Cookie("email", URLEncoder.encode(email, StandardCharsets.UTF_8));
            emailCookie.setHttpOnly(true);
            emailCookie.setPath("/");
            emailCookie.setMaxAge(30 * 60);
            response.addCookie(emailCookie);


            response.sendRedirect(frontendBaseUrl + "/register?oauth=true");
            return;
        }

        User user = optionalUser.get();

        if (user.getName() == null || user.getPhone() == null || user.getName().isEmpty() || user.getPhone().isEmpty()) {


            Cookie emailCookie = new Cookie("email", URLEncoder.encode(email, StandardCharsets.UTF_8));
            emailCookie.setHttpOnly(true);
            emailCookie.setPath("/");
            emailCookie.setMaxAge(30 * 60);
            response.addCookie(emailCookie);

            response.sendRedirect(frontendBaseUrl + "/register?oauth=true");
            return;
        }

        String accessToken = tokenProvider.generateAccessToken(authentication);
        String encodedAccessToken = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);

        Cookie emailCookie = new Cookie("email", URLEncoder.encode(email, StandardCharsets.UTF_8));
        Cookie accessTokenCookie = new Cookie("accessToken", encodedAccessToken);

        emailCookie.setHttpOnly(true);
        emailCookie.setPath("/");
        emailCookie.setMaxAge(30 * 60);

        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(30 * 60);

        response.addCookie(emailCookie);
        response.addCookie(accessTokenCookie);


        response.sendRedirect(frontendBaseUrl);
    }

}
