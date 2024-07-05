package kr.or.kosa.cmsplusmain.domain.billing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBilling is a Querydsl query type for Billing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBilling extends EntityPathBase<Billing> {

    private static final long serialVersionUID = 236288547L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBilling billing = new QBilling("billing");

    public final kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity _super = new kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity(this);

    public final DatePath<java.time.LocalDate> billingDate = createDate("billingDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> billingEndDate = createDate("billingEndDate", java.time.LocalDate.class);

    public final ListPath<BillingProduct, QBillingProduct> billingProducts = this.<BillingProduct, QBillingProduct>createList("billingProducts", BillingProduct.class, QBillingProduct.class, PathInits.DIRECT2);

    public final QBillingStandard billingStandard;

    public final DatePath<java.time.LocalDate> billingStartDate = createDate("billingStartDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedDateTime = _super.deletedDateTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memo = createString("memo");

    public final EnumPath<BillingStatus> status = createEnum("status", BillingStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public QBilling(String variable) {
        this(Billing.class, forVariable(variable), INITS);
    }

    public QBilling(Path<? extends Billing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBilling(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBilling(PathMetadata metadata, PathInits inits) {
        this(Billing.class, metadata, inits);
    }

    public QBilling(Class<? extends Billing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.billingStandard = inits.isInitialized("billingStandard") ? new QBillingStandard(forProperty("billingStandard"), inits.get("billingStandard")) : null;
    }

}

