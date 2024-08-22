package pintoss.giftmall.domains.site_info.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSiteInfo is a Querydsl query type for SiteInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSiteInfo extends EntityPathBase<SiteInfo> {

    private static final long serialVersionUID = -2065946196L;

    public static final QSiteInfo siteInfo = new QSiteInfo("siteInfo");

    public final StringPath address = createString("address");

    public final StringPath businesses = createString("businesses");

    public final StringPath businessHour = createString("businessHour");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath kakao = createString("kakao");

    public final StringPath name = createString("name");

    public final StringPath openChat = createString("openChat");

    public final StringPath owner = createString("owner");

    public final StringPath reportNumber = createString("reportNumber");

    public final StringPath tel = createString("tel");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QSiteInfo(String variable) {
        super(SiteInfo.class, forVariable(variable));
    }

    public QSiteInfo(Path<? extends SiteInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSiteInfo(PathMetadata metadata) {
        super(SiteInfo.class, metadata);
    }

}

