package kr.or.kosa.cmsplusmain.domain.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCardAutoPayment is a Querydsl query type for CardAutoPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardAutoPayment extends EntityPathBase<CardAutoPayment> {

    private static final long serialVersionUID = -670076956L;

    public static final QCardAutoPayment cardAutoPayment = new QCardAutoPayment("cardAutoPayment");

    public final QAutoPayment _super = new QAutoPayment(this);

    public final StringPath cardNumber = createString("cardNumber");

    public final StringPath cardOwner = createString("cardOwner");

    public final DatePath<java.time.LocalDate> cardOwnerBirth = createDate("cardOwnerBirth", java.time.LocalDate.class);

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

    public QCardAutoPayment(String variable) {
        super(CardAutoPayment.class, forVariable(variable));
    }

    public QCardAutoPayment(Path<? extends CardAutoPayment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCardAutoPayment(PathMetadata metadata) {
        super(CardAutoPayment.class, metadata);
    }

}

