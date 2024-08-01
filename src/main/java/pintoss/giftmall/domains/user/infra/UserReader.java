package pintoss.giftmall.domains.user.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.user.domain.User;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("사용자 id를 다시 확인해주세요."));
    }

}
