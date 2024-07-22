package pintoss.giftmall.domains.user.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByNameAndEmail(String name, String email);

    Optional<User> findByEmailAndPassword(String email, String password);

}
