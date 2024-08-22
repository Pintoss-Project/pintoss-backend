package pintoss.giftmall.domains.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 971154287L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> discountPrice = createNumber("discountPrice", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isOrderInCart = createBoolean("isOrderInCart");

    public final BooleanPath isSent = createBoolean("isSent");

    public final StringPath orderNo = createString("orderNo");

    public final NumberPath<Integer> orderPrice = createNumber("orderPrice", Integer.class);

    public final EnumPath<pintoss.giftmall.common.enums.OrderStatus> orderStatus = createEnum("orderStatus", pintoss.giftmall.common.enums.OrderStatus.class);

    public final EnumPath<pintoss.giftmall.common.enums.PayMethod> payMethod = createEnum("payMethod", pintoss.giftmall.common.enums.PayMethod.class);

    public final EnumPath<pintoss.giftmall.common.enums.PayStatus> payStatus = createEnum("payStatus", pintoss.giftmall.common.enums.PayStatus.class);

    public final pintoss.giftmall.domains.user.domain.QUser user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new pintoss.giftmall.domains.user.domain.QUser(forProperty("user")) : null;
    }

}

