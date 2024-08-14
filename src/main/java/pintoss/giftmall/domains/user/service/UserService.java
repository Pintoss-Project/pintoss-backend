package pintoss.giftmall.domains.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.common.exceptions.client.UnauthorizedException;
import pintoss.giftmall.common.oauth.TokenProvider;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.dto.UserResponse;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserResponse getUserInfo(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        String email = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        if (!user.isActive()) {
            throw new UnauthorizedException("비활성화된 사용자입니다.");
        }

        return UserResponse.fromEntity(user);
    }

    public Page<UserResponse> findUsersByDateAndKeywordPaged(LocalDate startDate, LocalDate endDate, String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findUsersByDateAndKeywordPaged(startDate, endDate, keyword, pageRequest);
        return usersPage.map(UserResponse::fromEntity);
    }

}
