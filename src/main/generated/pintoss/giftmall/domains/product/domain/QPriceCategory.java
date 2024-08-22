package pintoss.giftmall.domains.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPriceCategory is a Querydsl query type for PriceCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPriceCategory extends EntityPathBase<PriceCategory> {

    private static final long serialVersionUID = 1924347591L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPriceCategory priceCategory = new QPriceCategory("priceCategory");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QProduct product;

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QPriceCategory(String variable) {
        this(PriceCategory.class, forVariable(variable), INITS);
    }

    public QPriceCategory(Path<? extends PriceCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPriceCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPriceCategory(PathMetadata metadata, PathInits inits) {
        this(PriceCategory.class, metadata, inits);
    }

    public QPriceCategory(Class<? extends PriceCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product")) : null;
    }

}

