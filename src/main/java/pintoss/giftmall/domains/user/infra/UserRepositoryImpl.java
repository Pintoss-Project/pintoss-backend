package pintoss.giftmall.domains.user.infra;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pintoss.giftmall.domains.user.domain.QUser;
import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<User> findUsersByDateAndKeywordPaged(LocalDate startDate, LocalDate endDate, String keyword, PageRequest pageRequest) {
        QUser user = QUser.user;

        BooleanExpression dateCondition = betweenDates(user, startDate, endDate);
        BooleanExpression keywordCondition = containKeyword(user, keyword);

        List<User> users = queryFactory.selectFrom(user)
                .where(dateCondition, keywordCondition)
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(user)
                .where(dateCondition, keywordCondition)
                .fetchCount();

        return new PageImpl<>(users, pageRequest, total);
    }

    private BooleanExpression betweenDates(QUser user, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return user.createdAt.between(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        } else if (startDate != null) {
            return user.createdAt.after(startDate.atStartOfDay());
        } else if (endDate != null) {
            return user.createdAt.before(endDate.atTime(23, 59, 59));
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
