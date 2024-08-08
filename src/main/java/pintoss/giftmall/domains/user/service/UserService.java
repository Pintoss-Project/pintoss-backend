package pintoss.giftmall.domains.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
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
        return UserResponse.fromEntity(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserResponse> findUsersByDateAndKeyword(LocalDate startDate, LocalDate endDate, String keyword) {
        return userRepository.findUsersByDateAndKeyword(startDate, endDate, keyword).stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
