package pintoss.giftmall.domains.user.infra;

import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepositoryCustom {

    List<User> findUsersByDateAndKeyword(LocalDateTime startDate, LocalDateTime endDate, String keyword);

}
