//package kr.or.kosa.cmsplusmain.domain.contract.repository;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import jakarta.persistence.EntityManager;
//import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
//import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
//import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractSearchReq;
//import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
//
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional
//class ContractCustomRepositoryTest {
//
//	@Autowired
//	private ContractCustomRepository repository;
//
//	@Autowired
//	private EntityManager em;
//
//	@Test
//	@DisplayName("삭제된 계약은 조회되지 않는다.")
//	void shouldNotFetchedDeletedContracts() {
//
//		// // given
//		// Long vendorId = 1L;
//		// ContractSearchReq contractSearch = new ContractSearchReq();
//		// PageReq pageable = new PageReq();
//		//
//		// // when
//		// List<Contract> before = repository.findContractListWithCondition(
//		// 	vendorId,
//		// 	contractSearch,
//		// 	pageable);
//		//
//		// Contract removed = before.get(0);
//		// removed.delete();
//		// em.merge(removed);
//		//
//		// List<Contract> after = repository.findContractListWithCondition(
//		// 	vendorId,
//		// 	contractSearch,
//		// 	pageable);
//		//
//		// // then
//		// assertFalse(after.contains(removed));
//		// assertEquals(before.size() - 1, after.size());
//	}
//}