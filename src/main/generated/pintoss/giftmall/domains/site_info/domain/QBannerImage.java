package pintoss.giftmall.domains.site_info.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBannerImage is a Querydsl query type for BannerImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBannerImage extends EntityPathBase<BannerImage> {

    private static final long serialVersionUID = -2032943848L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBannerImage bannerImage = new QBannerImage("bannerImage");

    public final pintoss.giftmall.domains.image.domain.QImage _super = new pintoss.giftmall.domains.image.domain.QImage(this);

    public final QBanner banner;

    public final StringPath category = createString("category");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath url = _super.url;

    public QBannerImage(String variable) {
        this(BannerImage.class, forVariable(variable), INITS);
    }

    public QBannerImage(Path<? extends BannerImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBannerImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBannerImage(PathMetadata metadata, PathInits inits) {
        this(BannerImage.class, metadata, inits);
    }

    public QBannerImage(Class<? extends BannerImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.banner = inits.isInitialized("banner") ? new QBanner(forProperty("banner")) : null;
    }

}

