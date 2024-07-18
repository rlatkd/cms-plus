package kr.or.kosa.cmsplusmain.domain.simpconsent.simpinfo.dto;

import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractCreateReq;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractProductReq;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductListItemRes;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class SimpleConsentContractDTO {
    private String contractName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer contractDay;
    private List<ProductListItemRes> items;

    public ContractCreateReq toContractCreateReq() {
        List<ContractProductReq> contractProducts = items.stream()
                .map(item -> ContractProductReq.builder()
                        .productId(item.getProductId())
                        .price(item.getProductPrice())  // ProductListItemRes에서 가격 정보를 가져옵니다.
                        .quantity(1)  // 수량은 1로 고정되어 있습니다. 필요에 따라 변경 가능합니다.
                        .build())
                .collect(Collectors.toList());

        return ContractCreateReq.builder()
                .contractName(contractName)
                .contractStartDate(startDate)
                .contractEndDate(endDate)
                .contractDay(contractDay)
                .contractProducts(contractProducts)
                .build();
    }
}
