package pintoss.giftmall.domains.user.infra;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pintoss.giftmall.domains.user.domain.QUser;
import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findUsersByDateAndKeyword(LocalDateTime startDate, LocalDateTime endDate, String keyword) {
        QUser user = QUser.user;

        BooleanExpression dateCondition = betweenDates(user, startDate, endDate);
        BooleanExpression keywordCondition = containKeyword(user, keyword);

        return queryFactory.selectFrom(user)
                .where(dateCondition, keywordCondition)
                .fetch();
    }

    private BooleanExpression betweenDates(QUser user, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return user.createdAt.between(startDate, endDate);
        }
        return null;
    }

    private BooleanExpression containKeyword(QUser user, String keyword) {
        if (StringUtils.hasText(keyword)) {
            return user.email.containsIgnoreCase(keyword).or(user.phone.containsIgnoreCase(keyword));
        }
        return null;
    }

}
