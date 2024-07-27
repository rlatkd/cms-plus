package kr.or.kosa.cmsplusmain.domain.contract.repository;

import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.kosa.cmsplusmain.domain.base.repository.V2BaseRepository;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;

@Repository
public class V2ContractProductRepository extends V2BaseRepository<ContractProduct, Long> {

	public List<ContractProduct> findAllByContractId(Long contractId) {
		return selectFrom(contractProduct)
			.where(contractProduct.contract.id.eq(contractId))
			.fetch();
	}
}
