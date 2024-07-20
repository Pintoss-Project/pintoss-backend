package pintoss.giftmall.domains.user.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.service.port.AuthRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User register(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> login(String email, String password) {
        return userJpaRepository.findByEmailAndPassword(email, password);
    }

}
