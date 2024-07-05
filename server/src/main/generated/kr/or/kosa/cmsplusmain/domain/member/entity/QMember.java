package kr.or.kosa.cmsplusmain.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1472831953L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity _super = new kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final BooleanPath autoBilling = createBoolean("autoBilling");

    public final BooleanPath autoInvoiceSend = createBoolean("autoInvoiceSend");

    public final ListPath<kr.or.kosa.cmsplusmain.domain.contract.entity.Contract, kr.or.kosa.cmsplusmain.domain.contract.entity.QContract> contracts = this.<kr.or.kosa.cmsplusmain.domain.contract.entity.Contract, kr.or.kosa.cmsplusmain.domain.contract.entity.QContract>createList("contracts", kr.or.kosa.cmsplusmain.domain.contract.entity.Contract.class, kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedDateTime = _super.deletedDateTime;

    public final StringPath email = createString("email");

    public final DatePath<java.time.LocalDate> enrollDate = createDate("enrollDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<kr.or.kosa.cmsplusmain.domain.messaging.MessageSendMethod> invoiceSendMethod = createEnum("invoiceSendMethod", kr.or.kosa.cmsplusmain.domain.messaging.MessageSendMethod.class);

    public final StringPath memo = createString("memo");

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final EnumPath<MemberStatus> status = createEnum("status", MemberStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public final kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor vendor;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vendor = inits.isInitialized("vendor") ? new kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor(forProperty("vendor"), inits.get("vendor")) : null;
    }

}

