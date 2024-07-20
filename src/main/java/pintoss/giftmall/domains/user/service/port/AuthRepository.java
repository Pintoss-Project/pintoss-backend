package pintoss.giftmall.domains.user.service.port;

import pintoss.giftmall.domains.user.domain.User;

import java.util.Optional;

public interface AuthRepository {

    User register(User user);

    Optional<User> login(String email, String password);

}
