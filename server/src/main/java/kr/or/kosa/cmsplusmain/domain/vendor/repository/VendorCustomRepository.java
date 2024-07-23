package kr.or.kosa.cmsplusmain.domain.vendor.repository;


import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;

@Repository
public class VendorCustomRepository extends BaseCustomRepository<Vendor> {

	public VendorCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
		super(em, jpaQueryFactory);
	}

	/*
	 * 이미 존재하는 아이디인 경우: true
	 * */
	public boolean isExistUsername(String username) {
		Integer count = jpaQueryFactory
			.selectOne()
			.from(vendor)
			.where(vendor.username.eq(username), vendor.deleted.eq(false))
			.fetchFirst();

		return count != null;
	}

	/*
	 * 아이디로 고객 찾기
	 * */
	public Vendor findByUsername(String username) {
		return jpaQueryFactory
			.selectFrom(vendor)
			.where(vendor.username.eq(username), vendor.deleted.eq(false))
			.fetchOne();
	}

	/*
	 * 이름과 전화번호로 고객 찾기
	 * */
	public Vendor findByNameAndPhone(String name, String phone) {
		return jpaQueryFactory
				.selectFrom(vendor)
				.where(
						vendor.name.eq(name),
						vendor.phone.eq(phone),
						vendorNotDel()
				)
				.fetchOne();
	}

	public Vendor findByNameAndEmail(String name, String email) {
		return jpaQueryFactory
				.selectFrom(vendor)
				.where(
						vendor.name.eq(name),
						vendor.email.eq(email),
						vendorNotDel()
				)
				.fetchOne();
	}
}
