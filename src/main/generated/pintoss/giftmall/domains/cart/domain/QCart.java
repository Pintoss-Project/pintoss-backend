package pintoss.giftmall.domains.cart.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCart is a Querydsl query type for Cart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCart extends EntityPathBase<Cart> {

    private static final long serialVersionUID = 2122318439L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCart cart = new QCart("cart");

    public final NumberPath<java.math.BigDecimal> cardDiscount = createNumber("cardDiscount", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<pintoss.giftmall.common.enums.PayMethod> payMethod = createEnum("payMethod", pintoss.giftmall.common.enums.PayMethod.class);

    public final NumberPath<java.math.BigDecimal> phoneDiscount = createNumber("phoneDiscount", java.math.BigDecimal.class);

    public final pintoss.giftmall.domains.product.domain.QPriceCategory priceCategory;

    public final pintoss.giftmall.domains.product.domain.QProduct product;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final pintoss.giftmall.domains.user.domain.QUser user;

    public QCart(String variable) {
        this(Cart.class, forVariable(variable), INITS);
    }

    public QCart(Path<? extends Cart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCart(PathMetadata metadata, PathInits inits) {
        this(Cart.class, metadata, inits);
    }

    public QCart(Class<? extends Cart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.priceCategory = inits.isInitialized("priceCategory") ? new pintoss.giftmall.domains.product.domain.QPriceCategory(forProperty("priceCategory"), inits.get("priceCategory")) : null;
        this.product = inits.isInitialized("product") ? new pintoss.giftmall.domains.product.domain.QProduct(forProperty("product")) : null;
        this.user = inits.isInitialized("user") ? new pintoss.giftmall.domains.user.domain.QUser(forProperty("user")) : null;
    }

}

