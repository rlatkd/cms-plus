package kr.or.kosa.cmsplusmain.domain.vendor.repository;


import static kr.or.kosa.cmsplusmain.domain.member.entity.QMember.member;
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
	 * 고객 존재 여부 판단 - by 고객 username
	 */
	public boolean isExistUsername(String username) {
		Integer res = jpaQueryFactory
				.selectOne()
				.from(vendor)
				.where(
						vendor.username.eq(username),
						vendorNotDel()
				)
				.fetchOne();
		return res != null;
	}

	/*
	 * 고객 존재 여부 판단 - by 고객 phone
	 */
	public boolean isExistPhone(String phone) {
		Integer res = jpaQueryFactory
				.selectOne()
				.from(vendor)
				.where(
						vendor.phone.eq(phone),
						vendorNotDel()
				)
				.fetchOne();
		return res != null;
	}

	/*
	 * 고객 존재 여부 판단 - by 고객 email
	 */
	public boolean isExistEmail(String email) {
		Integer res = jpaQueryFactory
				.selectOne()
				.from(vendor)
				.where(
						vendor.email.eq(email),
						vendorNotDel()
				)
				.fetchOne();
		return res != null;
	}

	/*
	 * 아이디로 고객 찾기
	 * */
	public Vendor findByUsername(String username) {
		return jpaQueryFactory
			.selectFrom(vendor)
			.where(
					vendor.username.eq(username),
					vendorNotDel()
			)
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

	/*
	 * 이름과 이메일로 고객 찾기
	 * */
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
