package pintoss.giftmall.domains.user.infra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepositoryCustom {

    Page<User> findUsersByDateAndKeywordPaged(LocalDate startDate, LocalDate endDate, String keyword, PageRequest pageRequest);

}
