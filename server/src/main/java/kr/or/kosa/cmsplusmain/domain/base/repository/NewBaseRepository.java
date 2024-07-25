package kr.or.kosa.cmsplusmain.domain.base.repository;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;

@Transactional(readOnly = true)
@Repository
public abstract class NewBaseRepository<E extends BaseEntity, ID> {

	@PersistenceContext
	private EntityManager em;
	@Autowired
	private JPAQueryFactory queryFactory;
	private JpaEntityInformation<E, ID> entityInformation;	// lazy loading

	/**
	 * 저장 혹은 수정 시 사용
	 * 수정은 transactional readonly false 사용해서 save 호출없이 가능
	 * */
	@Transactional
	public E save(E entity) {
		if (isNewEntity(entity)) {
			em.persist(entity);
			return entity;
		}

		return em.merge(entity);
	}

	/**
	 * 삭제(소프트 딜리트) 검사를 할 항목을 두번쨰 인자[배열]로 준다.
	 * 예시.
	 * 청구를 select 하고, [청구, 계약, 청구상품]을 삭제된 것은 빼고 보여준다.
	 * => select(billing, billing, contract, billingProduct)
	 * */

	@SafeVarargs
	protected final <T> JPAQuery<T> select(Expression<T> expr, EntityPathBase<? extends BaseEntity>... notDelEntities) {
		return withNotDel(queryFactory.select(expr), notDelEntities);
	}

	@SafeVarargs
	protected final JPAQuery<Integer> selectOne(EntityPathBase<? extends BaseEntity>... notDelEntities) {
		return withNotDel(queryFactory.selectOne(), notDelEntities);
	}

	@SafeVarargs
	protected final JPAQuery<?> from(EntityPath<? extends BaseEntity> expr,
		EntityPathBase<? extends BaseEntity>... notDelEntities) {
		return withNotDel(queryFactory.from(expr), notDelEntities);
	}

	protected final JPAUpdateClause update(EntityPath<? extends BaseEntity> entity) {
		return queryFactory.update(entity);
	}

	protected final BooleanExpression isNotDeleted(EntityPathBase<? extends BaseEntity> entity) {
		try {
			BooleanPath deletedPath = (BooleanPath) entity.getClass().getField("deleted").get(entity);
			return deletedPath.isFalse();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException("소프트 딜리트 대상 엔티티가 아닙니다: " + entity.getType().getSimpleName(), e);
		}
	}

	protected final <T> JPAQuery<T> applyPagingAndSort(JPAQuery<T> query, PageReq pageReq) {
		return query
			.offset(pageReq.getPage())
			.limit(pageReq.getSize());
	}

	@SafeVarargs
	private <T> JPAQuery<T> withNotDel(JPAQuery<T> query, EntityPathBase<? extends BaseEntity>... notDelEntities) {
		if (notDelEntities == null || notDelEntities.length == 0) {
			return query;
		}
		BooleanExpression notDelConditions = Arrays.stream(notDelEntities)
			.map(this::isNotDeleted)
			.reduce(BooleanExpression::and)
			.orElse(null);

		return query.where(notDelConditions);
	}

	@SuppressWarnings("unchecked")
	private JpaEntityInformation<E, ID> getJpaEntityInformation(Class<?> clazz) {
		if (this.entityInformation == null) {
			this.entityInformation =
				(JpaEntityInformation<E, ID>) JpaEntityInformationSupport.getEntityInformation(clazz, em);
		}

		return this.entityInformation;
	}

	private Boolean isNewEntity(E entity) {
		return this.getJpaEntityInformation(entity.getClass()).isNew(entity);
	}
}