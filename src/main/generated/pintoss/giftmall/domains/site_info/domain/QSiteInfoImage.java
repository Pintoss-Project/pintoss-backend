package pintoss.giftmall.domains.site_info.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSiteInfoImage is a Querydsl query type for SiteInfoImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSiteInfoImage extends EntityPathBase<SiteInfoImage> {

    private static final long serialVersionUID = 1158593295L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSiteInfoImage siteInfoImage = new QSiteInfoImage("siteInfoImage");

    public final pintoss.giftmall.domains.image.domain.QImage _super = new pintoss.giftmall.domains.image.domain.QImage(this);

    public final EnumPath<pintoss.giftmall.common.enums.SiteInfoImageCategory> category = createEnum("category", pintoss.giftmall.common.enums.SiteInfoImageCategory.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QSiteInfo siteInfo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath url = _super.url;

    public QSiteInfoImage(String variable) {
        this(SiteInfoImage.class, forVariable(variable), INITS);
    }

    public QSiteInfoImage(Path<? extends SiteInfoImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSiteInfoImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSiteInfoImage(PathMetadata metadata, PathInits inits) {
        this(SiteInfoImage.class, metadata, inits);
    }

    public QSiteInfoImage(Class<? extends SiteInfoImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.siteInfo = inits.isInitialized("siteInfo") ? new QSiteInfo(forProperty("siteInfo")) : null;
    }

}

