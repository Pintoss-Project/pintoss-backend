package pintoss.giftmall.domains.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pintoss.giftmall.common.oauth.TokenProvider;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.dto.NaverCallbackRequest;
import pintoss.giftmall.domains.user.dto.NaverUserResponse;
import pintoss.giftmall.domains.user.infra.UserRepository;
import pintoss.giftmall.domains.user.service.NaverOAuthService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth/naver")
public class NaverOAuthController {

    private final NaverOAuthService naverOAuthService;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private String clientId = "ntP6_ogtZLkuD6J774IT";

    private String redirectUri = "https://pintossmall2.com/api/oauth/naver/callback";

    private String clientSecret = "9cIj0Lo1b_";

    @GetMapping("/connect")
    public void redirectToNaverLogin(HttpSession session, HttpServletResponse response) throws IOException {
        String state = UUID.randomUUID().toString();
        session.setAttribute("oauthState", state);

        String naverLoginUrl = String.format(
                "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s",
                clientId, URLEncoder.encode(redirectUri, StandardCharsets.UTF_8), URLEncoder.encode(state, StandardCharsets.UTF_8)
        );

        response.sendRedirect(naverLoginUrl);
    }

    @GetMapping("/callback")
    public ApiResponse<String> connectNaver(@ModelAttribute NaverCallbackRequest request) {
        String accessToken = naverOAuthService.getAccessToken(request.getCode(),request.getState());

        NaverUserResponse naverUser = naverOAuthService.getUserInfo(accessToken);

        Optional<User> userOpt = userRepository.findByEmail(naverUser.getResponse().getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            user.connectNaver();
            userRepository.save(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, List.of(new SimpleGrantedAuthority(user.getRole().name())));
            String jwtToken = tokenProvider.generateAccessToken(authentication);

            return ApiResponse.ok(jwtToken);
        } else {
            return ApiResponse.of(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. 회원가입 후에 연동해주세요.");
        }
    }

}
