package kr.or.kosa.cmsplusmain.domain.contract.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberContractListItemDto {
    private final Long contractId;                                    // 계약 ID
    private final String contractName;
    private final LocalDate contractStartDate;
    private final LocalDate contractEndDate;
    private final int contractDay;
    private final List<ContractProductRes> contractProducts;
    private final Long contractPrice ;

    public static MemberContractListItemDto fromEntity(Contract contract) {
        final List<ContractProductRes> contractProductResList = contract.getContractProducts()
                .stream()
                .map(ContractProductRes::fromEntity)
                .toList();

        return MemberContractListItemDto.builder()
                .contractId(contract.getId())
                .contractName(contract.getContractName())
                .contractStartDate(contract.getContractStartDate())
                .contractEndDate(contract.getContractEndDate())
                .contractDay(contract.getContractDay())
                .contractProducts(contractProductResList)
                .contractPrice(contract.getContractPrice())
                .build();
    }

}
