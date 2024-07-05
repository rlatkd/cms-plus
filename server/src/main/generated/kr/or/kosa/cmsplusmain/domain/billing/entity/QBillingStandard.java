package kr.or.kosa.cmsplusmain.domain.billing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBillingStandard is a Querydsl query type for BillingStandard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBillingStandard extends EntityPathBase<BillingStandard> {

    private static final long serialVersionUID = 1567500800L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBillingStandard billingStandard = new QBillingStandard("billingStandard");

    public final kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity _super = new kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity(this);

    public final kr.or.kosa.cmsplusmain.domain.contract.entity.QContract contract;

    public final DatePath<java.time.LocalDate> contractDate = createDate("contractDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedDateTime = _super.deletedDateTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kr.or.kosa.cmsplusmain.domain.member.entity.QMember member;

    public final EnumPath<BillingStandardStatus> status = createEnum("status", BillingStandardStatus.class);

    public final EnumPath<BillingType> type = createEnum("type", BillingType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public QBillingStandard(String variable) {
        this(BillingStandard.class, forVariable(variable), INITS);
    }

    public QBillingStandard(Path<? extends BillingStandard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBillingStandard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBillingStandard(PathMetadata metadata, PathInits inits) {
        this(BillingStandard.class, metadata, inits);
    }

    public QBillingStandard(Class<? extends BillingStandard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contract = inits.isInitialized("contract") ? new kr.or.kosa.cmsplusmain.domain.contract.entity.QContract(forProperty("contract"), inits.get("contract")) : null;
        this.member = inits.isInitialized("member") ? new kr.or.kosa.cmsplusmain.domain.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

