package pintoss.giftmall.domains.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1254944177L;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<java.math.BigDecimal> cardDiscount = createNumber("cardDiscount", java.math.BigDecimal.class);

    public final EnumPath<pintoss.giftmall.common.enums.ProductCategory> category = createEnum("category", pintoss.giftmall.common.enums.ProductCategory.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath csCenter = createString("csCenter");

    public final StringPath description = createString("description");

    public final StringPath homePage = createString("homePage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPopular = createBoolean("isPopular");

    public final StringPath name = createString("name");

    public final NumberPath<java.math.BigDecimal> phoneDiscount = createNumber("phoneDiscount", java.math.BigDecimal.class);

    public final StringPath publisher = createString("publisher");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

