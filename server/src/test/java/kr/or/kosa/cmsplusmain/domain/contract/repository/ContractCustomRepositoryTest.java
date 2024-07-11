package kr.or.kosa.cmsplusmain.domain.contract.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.config.QueryDslConfig;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ContractCustomRepositoryTest {

	@Autowired
	private ContractCustomRepository repository;

	@Autowired
	private EntityManager em;

	@Test
	@DisplayName("삭제된 계약은 조회되지 않는다.")
	void shouldNotFetchedDeletedContracts() {

		// given
		String vendorUsername = "vendor1";
		ContractSearch contractSearch = new ContractSearch();
		SortPageDto.Req pageable = new SortPageDto.Req();

		// when
		List<Contract> before = repository.findContractListWithCondition(
			vendorUsername,
			contractSearch,
			pageable);

		Contract removed = before.get(0);
		removed.delete();
		em.merge(removed);
		em.flush();
		em.clear();  // 영속성 컨텍스트 초기화

		List<Contract> after = repository.findContractListWithCondition(
			vendorUsername,
			contractSearch,
			pageable);

		// then
		assertFalse(after.contains(removed));
		assertEquals(before.size() - 1, after.size());
	}
}