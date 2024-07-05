package kr.or.kosa.cmsplusmain.domain.base.repository;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public abstract class BaseRepository<T extends BaseEntity, ID extends Serializable> {

	private final EntityManager em;
	protected final JPAQueryFactory jpaQueryFactory;

	/*
	* 새로생성 혹은 수정완료시 (비영속 상태 객체 수정 완료 후 호출)
	* */
	@Transactional
	public T save(T entity) {
		return em.merge(entity);
	}

	/*
	* 소프트 삭제
	* */
	@Transactional
	public void delete(T entity) {
		entity.delete();
		em.merge(entity);
	}
}
