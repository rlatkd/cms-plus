package kr.or.kosa.cmsplusmain.domain.contract.repository;

import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.*;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.*;
import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.*;
import static kr.or.kosa.cmsplusmain.domain.payment.entity.QPayment.*;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractSearch;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractProduct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ContractCustomRepository extends BaseCustomRepository<Contract> {

	public ContractCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
		super(em, jpaQueryFactory);
	}

	/*
	 * 계약 목록 조회
	 *
	 *  */
	public List<Contract> findContractListWithCondition(String vendorUsername, ContractSearch search,
		SortPageDto.Req pageable) {
		return jpaQueryFactory
			.selectFrom(contract)

			.join(contract.vendor, vendor)
			.join(contract.member, member).fetchJoin()
			.leftJoin(contract.contractProducts, contractProduct).on(contractProductNotDel())    // left join
			.join(contract.payment, payment).fetchJoin()

			.where(
				contractNotDel(),                                // 계약 소프트 삭제

				vendorUsernameEq(vendorUsername),                // 고객 일치

				memberNameContains(search.getMemberName()),        // 회원 이름 포함
				memberPhoneContains(search.getMemberPhone()),    // 회원 휴대번호 포함
				contractDayEq(search.getContractDay()),            // 약정일 일치
				contractStatusEq(search.getContractStatus()),    // 계약상태 일치
				consentStatusEq(search.getConsentStatus())        // 동의상태 일치
			)

			.groupBy(contract.id)
			.having(
				productNameContainsInGroup(search.getProductName()),
				contractPriceLoeInGroup(search.getContractPrice())
			)

			.orderBy(orderMethod(pageable))

			.offset(pageable.getPage())
			.limit(pageable.getSize())
			.fetch();
	}

	/*
	 * 계약 상세 조회
	 * */
	public Optional<Contract> findContractDetailById(Long id) {
		return Optional.ofNullable(jpaQueryFactory
			.selectFrom(contract)
			.join(contract.payment, payment).fetchJoin()
			.where(
				contractNotDel(),
				contract.id.eq(id))
			.fetchOne());
	}

	/*
	 * 계약 정보 수정
	 * */
	@Transactional
	public void updateContract(Long contractId, String contractName, List<ContractProduct> contractProducts) {

		// 기존 계약 상품 삭제
		jpaQueryFactory
			.update(contractProduct)
			.where(contractProduct.contract.id.eq(contractId))
			.set(contractProduct.deleted, true)
			.execute();

		// 새로운 계약 상품 추가
		contractProducts.forEach(em::persist);

		// 계약 이름 변경
		jpaQueryFactory
			.update(contract)
			.where(contract.id.eq(contractId))
			.set(contract.name, contractName)
			.execute();
	}
}
