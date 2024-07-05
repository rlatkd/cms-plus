package kr.or.kosa.cmsplusmain.domain.billing.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.or.kosa.cmsplusmain.domain.billing.dto.QBillingListItem is a Querydsl Projection type for BillingListItem
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBillingListItem extends ConstructorExpression<BillingListItem> {

    private static final long serialVersionUID = -852072630L;

    public QBillingListItem(com.querydsl.core.types.Expression<Long> billingId, com.querydsl.core.types.Expression<String> memberName, com.querydsl.core.types.Expression<java.time.LocalDate> billingDate, com.querydsl.core.types.Expression<? extends java.util.List<kr.or.kosa.cmsplusmain.domain.billing.entity.BillingProduct>> billingProducts, com.querydsl.core.types.Expression<kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus> billingStatus, com.querydsl.core.types.Expression<String> paymentType, com.querydsl.core.types.Expression<java.time.LocalDate> contractDate) {
        super(BillingListItem.class, new Class<?>[]{long.class, String.class, java.time.LocalDate.class, java.util.List.class, kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus.class, String.class, java.time.LocalDate.class}, billingId, memberName, billingDate, billingProducts, billingStatus, paymentType, contractDate);
    }

}

