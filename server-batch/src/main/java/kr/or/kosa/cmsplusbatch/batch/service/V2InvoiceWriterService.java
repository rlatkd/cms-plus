//package kr.or.kosa.cmsplusbatch.batch.service;
//
//import kr.or.kosa.cmsplusbatch.batch.dto.BillingQueryDto;
//import kr.or.kosa.cmsplusbatch.domain.billing.entity.Billing;
//import kr.or.kosa.cmsplusbatch.domain.billing.repository.BillingRepository;
//import kr.or.kosa.cmsplusbatch.domain.contract.repository.ContractRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class V2InvoiceWriterService implements ItemWriter<BillingQueryDto> {
//
//    private final BillingRepository billingRepository;
//    private final ContractRepository contractRepository;
//
//    @Override
//    public void write(Chunk<? extends BillingQueryDto> chunk) throws Exception {
//        for (BillingQueryDto billingDto : chunk) {
//            Billing billing = billingDto.toEntity(
//                    contractRepository.findById(billingDto.getContractId())
//                            .orElseThrow(() -> new IllegalArgumentException("Invalid contract ID: " + billingDto.getContractId()))
//            );
//            billingRepository.save(billing);
//        }
//    }
//}
