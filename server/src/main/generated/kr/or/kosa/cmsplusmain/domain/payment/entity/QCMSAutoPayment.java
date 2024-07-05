package kr.or.kosa.cmsplusmain.domain.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCMSAutoPayment is a Querydsl query type for CMSAutoPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCMSAutoPayment extends EntityPathBase<CMSAutoPayment> {

    private static final long serialVersionUID = 657943793L;

    public static final QCMSAutoPayment cMSAutoPayment = new QCMSAutoPayment("cMSAutoPayment");

    public final QAutoPayment _super = new QAutoPayment(this);

    public final StringPath accountNumber = createString("accountNumber");

    public final StringPath accountOwner = createString("accountOwner");

    public final DatePath<java.time.LocalDate> accountOwnerBirth = createDate("accountOwnerBirth", java.time.LocalDate.class);

    public final EnumPath<Bank> bank = createEnum("bank", Bank.class);

    //inherited
    public final StringPath consentImgUrl = _super.consentImgUrl;

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
    public final StringPath signImgUrl = _super.signImgUrl;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> simpleConsentReqDateTime = _super.simpleConsentReqDateTime;

    //inherited
    public final EnumPath<PaymentStatus> status = _super.status;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public QCMSAutoPayment(String variable) {
        super(CMSAutoPayment.class, forVariable(variable));
    }

    public QCMSAutoPayment(Path<? extends CMSAutoPayment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCMSAutoPayment(PathMetadata metadata) {
        super(CMSAutoPayment.class, metadata);
    }

}

