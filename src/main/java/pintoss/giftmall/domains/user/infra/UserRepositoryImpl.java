package pintoss.giftmall.domains.user.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.service.port.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByNameAndPhone(String name, String phone) {
        return userJpaRepository.findByNameAndPhone(name, phone);
    }

    @Override
    public Optional<User> findByNameAndEmail(String name, String email) {
        return userJpaRepository.findByNameAndEmail(name, email);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public List<User> findUsersByDateAndKeyword(LocalDateTime startDate, LocalDateTime endDate, String keyword) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }

}
