package kr.or.kosa.cmsplusmain.domain.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAutoPayment is a Querydsl query type for AutoPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAutoPayment extends EntityPathBase<AutoPayment> {

    private static final long serialVersionUID = -447335532L;

    public static final QAutoPayment autoPayment = new QAutoPayment("autoPayment");

    public final QPayment _super = new QPayment(this);

    public final StringPath consentImgUrl = createString("consentImgUrl");

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

    public final StringPath signImgUrl = createString("signImgUrl");

    public final DateTimePath<java.time.LocalDateTime> simpleConsentReqDateTime = createDateTime("simpleConsentReqDateTime", java.time.LocalDateTime.class);

    //inherited
    public final EnumPath<PaymentStatus> status = _super.status;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public QAutoPayment(String variable) {
        super(AutoPayment.class, forVariable(variable));
    }

    public QAutoPayment(Path<? extends AutoPayment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAutoPayment(PathMetadata metadata) {
        super(AutoPayment.class, metadata);
    }

}

