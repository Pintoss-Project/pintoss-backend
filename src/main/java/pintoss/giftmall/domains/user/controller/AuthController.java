package pintoss.giftmall.domains.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.user.dto.*;
import pintoss.giftmall.domains.user.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ApiResponse.ok(null);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request,HttpServletResponse httpResponse) {
        LoginResponse response = authService.login(request);
        // JWT를 쿠키로 설정
        setTokenInCookie(httpResponse, "accessToken", response.getAccessToken(), 3600);  // 1시간 유효
        setTokenInCookie(httpResponse, "refreshToken", response.getRefreshToken(), 604800); // 7일 유효
        return ApiResponse.ok(response);
    }

    @PostMapping("/reissue")
    public ApiResponse<?> reissue(@RequestBody RequestToken requestToken) {
        LoginResponse response = authService.refreshToken(requestToken.getAccessToken(), requestToken.getRefreshToken());
        return ApiResponse.ok(response);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ApiResponse.ok(null);
    }

    @GetMapping("/check-id")
    public ApiResponse<Boolean> checkEmailDuplicate(@RequestParam(name = "email") @NotBlank String email) {
        boolean isDuplicate = authService.checkEmailDuplicate(email);
        return ApiResponse.ok(isDuplicate);
    }

    @GetMapping("/check-phone")
    public ApiResponse<Boolean> checkPhoneDuplicate(@RequestParam(name = "phone") @NotBlank String phone) {
        boolean isDuplicate = authService.checkPhoneDuplicate(phone);
        return ApiResponse.ok(isDuplicate);
    }

    @GetMapping("/find-id")
    public ApiResponse<String> findUserId(@RequestParam(name = "name") @NotBlank String name, @RequestParam(name = "phone") @NotBlank String phone) {
        String email = authService.findUserIdByNameAndPhone(name, phone);
        return ApiResponse.ok(email);
    }

    @GetMapping("/find-password")
    public ApiResponse<Void> findPassword(@RequestParam(name = "name") @NotBlank String name, @RequestParam(name = "email") @NotBlank String email) {
        authService.findPassword(name, email);
        return ApiResponse.ok(null);
    }

    @PostMapping("/deactivate")
    public ApiResponse<Void> deactivateUser(@RequestHeader("Authorization") @NotBlank String token) {
        String cleanedToken = token.replace("bearer ", "");
        authService.deactivateUser(cleanedToken);
        return ApiResponse.ok(null);
    }

    @PatchMapping("/update")
    public ApiResponse<Void> updateUser(@RequestHeader("Authorization") @NotBlank String token,
                                        @RequestBody @Valid OAuthRegisterRequest request) {
        authService.updateUser(request.getEmail(), request);
        return ApiResponse.ok(null);
    }

    @PatchMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        authService.resetPassword(request.getName(), request.getPhone(), request.getNewPassword());
        return ApiResponse.ok(null);
    }

    @PostMapping("/send")
    public ApiResponse<?> SendSms(@RequestBody @Valid UserPhoneRequestDto request) {
        String code = authService.SendSms(request);
        return ApiResponse.ok(code);
    }

    private void setTokenInCookie(HttpServletResponse response, String name, String token, int maxAge) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true); // JavaScript 접근 불가
        cookie.setSecure(true);  // HTTPS에서만 동작 (테스트 환경에서는 false로 변경 가능)
        cookie.setPath("/");     // 전체 경로에서 유효
        cookie.setMaxAge(maxAge); // 유효 기간 (초 단위)
        response.addCookie(cookie);
    }

}
