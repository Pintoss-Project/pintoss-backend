package pintoss.giftmall.common.oauth;

import lombok.Builder;
import pintoss.giftmall.common.exceptions.AuthException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.user.domain.User;

import java.util.Map;

@Builder
public record OAuth2UserInfo(
        String email
) {

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "kakao" -> ofKakao(attributes);
            case "naver" -> ofNaver(attributes);
            default -> throw new AuthException(ErrorCode.ILLEGAL_REGISTRATION_ID, "Invalid registrationId: " + registrationId);
        };
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        // 이메일 정보가 없는 경우 처리
        if (account == null || account.get("email") == null) {
            throw new AuthException(ErrorCode.NOT_FOUND, "Email not found in Kakao response.");
        }

        return OAuth2UserInfo.builder()
                .email((String) account.get("email"))
                .build();
    }

    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        // 이메일 정보가 없는 경우 처리
        if (response == null || response.get("email") == null) {
            throw new AuthException(ErrorCode.NOT_FOUND, "Email not found in Naver response.");
        }

        return OAuth2UserInfo.builder()
                .email((String) response.get("email"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .build();
    }

}
