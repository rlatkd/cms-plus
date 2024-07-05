package kr.or.kosa.cmsplusmain.domain.contract.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContract is a Querydsl query type for Contract
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContract extends EntityPathBase<Contract> {

    private static final long serialVersionUID = 76680449L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContract contract = new QContract("contract");

    public final kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity _super = new kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity(this);

    public final NumberPath<Integer> contractDay = createNumber("contractDay", Integer.class);

    public final DatePath<java.time.LocalDate> contractEndDate = createDate("contractEndDate", java.time.LocalDate.class);

    public final NumberPath<Long> contractPrice = createNumber("contractPrice", Long.class);

    public final ListPath<ContractProduct, QContractProduct> contractProducts = this.<ContractProduct, QContractProduct>createList("contractProducts", ContractProduct.class, QContractProduct.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> contractStartDate = createDate("contractStartDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedDateTime = _super.deletedDateTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kr.or.kosa.cmsplusmain.domain.member.entity.QMember member;

    public final StringPath name = createString("name");

    public final kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment payment;

    public final EnumPath<ContractStatus> status = createEnum("status", ContractStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public final kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor vendor;

    public QContract(String variable) {
        this(Contract.class, forVariable(variable), INITS);
    }

    public QContract(Path<? extends Contract> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContract(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContract(PathMetadata metadata, PathInits inits) {
        this(Contract.class, metadata, inits);
    }

    public QContract(Class<? extends Contract> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new kr.or.kosa.cmsplusmain.domain.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
        this.payment = inits.isInitialized("payment") ? new kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment(forProperty("payment")) : null;
        this.vendor = inits.isInitialized("vendor") ? new kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor(forProperty("vendor"), inits.get("vendor")) : null;
    }

}

