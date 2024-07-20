package pintoss.giftmall.domains.user.service.port;

import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByNameAndEmail(String name, String email);

    List<User> findAll();

    List<User> findUsersByDateAndKeyword(LocalDateTime startDate, LocalDateTime endDate, String keyword);

    void deleteById(Long id);

}
