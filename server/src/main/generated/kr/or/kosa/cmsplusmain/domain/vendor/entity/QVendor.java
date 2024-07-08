package kr.or.kosa.cmsplusmain.domain.vendor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVendor is a Querydsl query type for Vendor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVendor extends EntityPathBase<Vendor> {

    private static final long serialVersionUID = -1049719123L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVendor vendor = new QVendor("vendor");

    public final kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity _super = new kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedDateTime = _super.deletedDateTime;

    public final StringPath department = createString("department");

    public final StringPath email = createString("email");

    public final StringPath homePhone = createString("homePhone");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final EnumPath<UserRole> role = createEnum("role", UserRole.class);

    public final kr.or.kosa.cmsplusmain.domain.settings.QSimpConsentSetting simpConsentSetting;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public final StringPath username = createString("username");

    public QVendor(String variable) {
        this(Vendor.class, forVariable(variable), INITS);
    }

    public QVendor(Path<? extends Vendor> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVendor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVendor(PathMetadata metadata, PathInits inits) {
        this(Vendor.class, metadata, inits);
    }

    public QVendor(Class<? extends Vendor> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.simpConsentSetting = inits.isInitialized("simpConsentSetting") ? new kr.or.kosa.cmsplusmain.domain.settings.QSimpConsentSetting(forProperty("simpConsentSetting"), inits.get("simpConsentSetting")) : null;
    }

}

