package kr.or.kosa.cmsplusmain.domain.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVirtualAccountPayment is a Querydsl query type for VirtualAccountPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVirtualAccountPayment extends EntityPathBase<VirtualAccountPayment> {

    private static final long serialVersionUID = -1952157439L;

    public static final QVirtualAccountPayment virtualAccountPayment = new QVirtualAccountPayment("virtualAccountPayment");

    public final QPayment _super = new QPayment(this);

    public final StringPath accountNumber = createString("accountNumber");

    public final StringPath accountOwner = createString("accountOwner");

    public final EnumPath<Bank> bank = createEnum("bank", Bank.class);

    //inherited
    public final EnumPath<ConsentStatus> consentStatus = _super.consentStatus;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedDateTime = _super.deletedDateTime;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final EnumPath<PaymentMethod> paymentMethod = _super.paymentMethod;

    //inherited
    public final StringPath paymentType = _super.paymentType;

    //inherited
    public final EnumPath<PaymentStatus> status = _super.status;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public QVirtualAccountPayment(String variable) {
        super(VirtualAccountPayment.class, forVariable(variable));
    }

    public QVirtualAccountPayment(Path<? extends VirtualAccountPayment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVirtualAccountPayment(PathMetadata metadata) {
        super(VirtualAccountPayment.class, metadata);
    }

}

