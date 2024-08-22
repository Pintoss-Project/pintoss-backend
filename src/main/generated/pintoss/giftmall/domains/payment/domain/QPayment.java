package pintoss.giftmall.domains.payment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = 941056367L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final DateTimePath<java.time.LocalDateTime> approvedAt = createDateTime("approvedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final pintoss.giftmall.domains.order.domain.QOrder order;

    public final EnumPath<pintoss.giftmall.common.enums.PayMethod> payMethod = createEnum("payMethod", pintoss.giftmall.common.enums.PayMethod.class);

    public final NumberPath<Integer> payPrice = createNumber("payPrice", Integer.class);

    public final EnumPath<pintoss.giftmall.common.enums.PayStatus> payStatus = createEnum("payStatus", pintoss.giftmall.common.enums.PayStatus.class);

    public final pintoss.giftmall.domains.user.domain.QUser user;

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new pintoss.giftmall.domains.order.domain.QOrder(forProperty("order"), inits.get("order")) : null;
        this.user = inits.isInitialized("user") ? new pintoss.giftmall.domains.user.domain.QUser(forProperty("user")) : null;
    }

}

