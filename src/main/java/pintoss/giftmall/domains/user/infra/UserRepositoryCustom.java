package pintoss.giftmall.domains.user.infra;

import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepositoryCustom {

    List<User> findUsersByDateAndKeyword(LocalDate startDate, LocalDate endDate, String keyword);

}
