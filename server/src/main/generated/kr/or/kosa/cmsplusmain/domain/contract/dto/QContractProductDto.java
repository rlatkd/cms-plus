package kr.or.kosa.cmsplusmain.domain.contract.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.or.kosa.cmsplusmain.domain.contract.dto.QContractProductDto is a Querydsl Projection type for ContractProductDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QContractProductDto extends ConstructorExpression<ContractProductDto> {

    private static final long serialVersionUID = -1429520627L;

    public QContractProductDto(com.querydsl.core.types.Expression<Long> contractProductId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<Integer> quantity) {
        super(ContractProductDto.class, new Class<?>[]{long.class, String.class, int.class, int.class}, contractProductId, name, price, quantity);
    }

}

