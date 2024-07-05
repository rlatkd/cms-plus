package kr.or.kosa.cmsplusmain.domain.contract.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.or.kosa.cmsplusmain.domain.contract.dto.QContractListItem is a Querydsl Projection type for ContractListItem
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QContractListItem extends ConstructorExpression<ContractListItem> {

    private static final long serialVersionUID = 1453751470L;

    public QContractListItem(com.querydsl.core.types.Expression<Long> contractId, com.querydsl.core.types.Expression<String> memberName, com.querydsl.core.types.Expression<String> memberPhone, com.querydsl.core.types.Expression<Integer> contractDay, com.querydsl.core.types.Expression<? extends java.util.List<kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct>> contractProducts, com.querydsl.core.types.Expression<Long> contractPrice, com.querydsl.core.types.Expression<kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus> contractStatus, com.querydsl.core.types.Expression<kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus> consentStatus) {
        super(ContractListItem.class, new Class<?>[]{long.class, String.class, String.class, int.class, java.util.List.class, long.class, kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus.class, kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus.class}, contractId, memberName, memberPhone, contractDay, contractProducts, contractPrice, contractStatus, consentStatus);
    }

}

