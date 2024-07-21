package pintoss.giftmall.domains.user.infra;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pintoss.giftmall.domains.user.domain.QUser;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.service.port.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final JPAQueryFactory queryFactory;

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
        QUser user = QUser.user;

        BooleanExpression dateCondition = betweenDates(user, startDate, endDate);
        BooleanExpression keywordCondition = containKeyword(user, keyword);

        return queryFactory.selectFrom(user)
                .where(dateCondition, keywordCondition)
                .fetch();
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }

    private BooleanExpression betweenDates(QUser user, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return user.createdAt.between(startDate, endDate);
        }
        return null;
    }

    private BooleanExpression containKeyword(QUser user, String keyword) {
        if (StringUtils.hasText(keyword) && !keyword.isEmpty()) {
            return user.email.containsIgnoreCase(keyword).or(user.phone.containsIgnoreCase(keyword));
        }
        return null;
    }

}
