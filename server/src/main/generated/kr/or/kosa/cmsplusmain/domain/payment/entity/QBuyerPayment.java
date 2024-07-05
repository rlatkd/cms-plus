package kr.or.kosa.cmsplusmain.domain.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBuyerPayment is a Querydsl query type for BuyerPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuyerPayment extends EntityPathBase<BuyerPayment> {

    private static final long serialVersionUID = -398780874L;

    public static final QBuyerPayment buyerPayment = new QBuyerPayment("buyerPayment");

    public final QPayment _super = new QPayment(this);

    public final SetPath<PaymentMethod, EnumPath<PaymentMethod>> availableMethods = this.<PaymentMethod, EnumPath<PaymentMethod>>createSet("availableMethods", PaymentMethod.class, EnumPath.class, PathInits.DIRECT2);

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

    public QBuyerPayment(String variable) {
        super(BuyerPayment.class, forVariable(variable));
    }

    public QBuyerPayment(Path<? extends BuyerPayment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBuyerPayment(PathMetadata metadata) {
        super(BuyerPayment.class, metadata);
    }

}

