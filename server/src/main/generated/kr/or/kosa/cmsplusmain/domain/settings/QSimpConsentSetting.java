package kr.or.kosa.cmsplusmain.domain.settings;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSimpConsentSetting is a Querydsl query type for SimpConsentSetting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSimpConsentSetting extends EntityPathBase<SimpConsentSetting> {

    private static final long serialVersionUID = 1137215610L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSimpConsentSetting simpConsentSetting = new QSimpConsentSetting("simpConsentSetting");

    public final kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity _super = new kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedDateTime = _super.deletedDateTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod, EnumPath<kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod>> simpConsentPayments = this.<kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod, EnumPath<kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod>>createSet("simpConsentPayments", kr.or.kosa.cmsplusmain.domain.payment.entity.PaymentMethod.class, EnumPath.class, PathInits.DIRECT2);

    public final SetPath<kr.or.kosa.cmsplusmain.domain.product.entity.Product, kr.or.kosa.cmsplusmain.domain.product.entity.QProduct> simpConsentProducts = this.<kr.or.kosa.cmsplusmain.domain.product.entity.Product, kr.or.kosa.cmsplusmain.domain.product.entity.QProduct>createSet("simpConsentProducts", kr.or.kosa.cmsplusmain.domain.product.entity.Product.class, kr.or.kosa.cmsplusmain.domain.product.entity.QProduct.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public final kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor vendor;

    public QSimpConsentSetting(String variable) {
        this(SimpConsentSetting.class, forVariable(variable), INITS);
    }

    public QSimpConsentSetting(Path<? extends SimpConsentSetting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSimpConsentSetting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSimpConsentSetting(PathMetadata metadata, PathInits inits) {
        this(SimpConsentSetting.class, metadata, inits);
    }

    public QSimpConsentSetting(Class<? extends SimpConsentSetting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vendor = inits.isInitialized("vendor") ? new kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor(forProperty("vendor"), inits.get("vendor")) : null;
    }

}

