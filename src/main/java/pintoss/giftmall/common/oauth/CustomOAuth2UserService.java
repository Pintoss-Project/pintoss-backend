package pintoss.giftmall.common.oauth;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("OAuth2 User Request received for client: " + userRequest.getClientRegistration().getRegistrationId());
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
        log.debug("OAuth2 User Attributes: " + oAuth2UserAttributes);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes);

        User user = getOrSaveUser(oAuth2UserInfo);

        return new PrincipalDetails(user, oAuth2UserAttributes, userNameAttributeName);
    }

    private User getOrSaveUser(OAuth2UserInfo oAuth2UserInfo) {
        Optional<User> optionalUser = userRepository.findByEmail(oAuth2UserInfo.email());

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            User newUser = oAuth2UserInfo.toEntity();
            newUser.updateEmail(oAuth2UserInfo.email());

            return newUser;
        }
    }
}
