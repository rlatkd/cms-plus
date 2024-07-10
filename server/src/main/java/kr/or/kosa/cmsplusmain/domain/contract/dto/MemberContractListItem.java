package kr.or.kosa.cmsplusmain.domain.contract.dto;

import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
public class MemberContractListItem {
    private final Long contractId;                                    // 계약 ID
    private final String contractName;
    private final String contractDate;
    private final int contractDay;
    private final List<ContractProductRes> contractProducts;
    private final Long contractPrice ;
    private final ContractStatus contractStatus;
    private final ConsentStatus consentStatus;

    public static MemberContractListItem fromEntity(Contract contract) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedContractDate = contract.getContractStartDate().format(formatter) + " ~ " + contract.getContractEndDate().format(formatter);

        final List<ContractProductRes> contractProductResList = contract.getContractProducts()
                .stream()
                .map(ContractProductRes::fromEntity)
                .toList();

        final Payment payment = contract.getPayment();
        final ConsentStatus consentStatus = payment.getConsentStatus();

        return MemberContractListItem.builder()
                .contractId(contract.getId())
                .contractName(contract.getName())
                .contractDate(formattedContractDate)
                .contractDay(contract.getContractDay())
                .contractProducts(contractProductResList)
                .contractPrice(contract.getContractPrice())
                .contractStatus(contract.getStatus())
                .consentStatus(consentStatus)
                .build();
    }

}
