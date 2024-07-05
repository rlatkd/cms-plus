package kr.or.kosa.cmsplusmain.domain.billing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBillingProduct is a Querydsl query type for BillingProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBillingProduct extends EntityPathBase<BillingProduct> {

    private static final long serialVersionUID = -1271083700L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBillingProduct billingProduct = new QBillingProduct("billingProduct");

    public final kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity _super = new kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity(this);

    public final QBilling billing;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedDateTime = _super.deletedDateTime;

    public final NumberPath<Integer> discountPrice = createNumber("discountPrice", Integer.class);

    public final NumberPath<Integer> extraPrice = createNumber("extraPrice", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kr.or.kosa.cmsplusmain.domain.product.entity.QProduct product;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public QBillingProduct(String variable) {
        this(BillingProduct.class, forVariable(variable), INITS);
    }

    public QBillingProduct(Path<? extends BillingProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBillingProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBillingProduct(PathMetadata metadata, PathInits inits) {
        this(BillingProduct.class, metadata, inits);
    }

    public QBillingProduct(Class<? extends BillingProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.billing = inits.isInitialized("billing") ? new QBilling(forProperty("billing"), inits.get("billing")) : null;
        this.product = inits.isInitialized("product") ? new kr.or.kosa.cmsplusmain.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

