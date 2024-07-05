package kr.or.kosa.cmsplusmain.domain.billing.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Comment("청구 생성 정보 (정기적으로 혹은 추가 청구를 만들기 위한 정보)")
@Entity
@Table(name = "billing_standard")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BillingStandard extends BaseEntity {

	@Id
	@Column(name = "billing_standard_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("청구한 회원")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	@NotNull
	private Member member;

	@Comment("청구 설정 상태")
	@Enumerated(EnumType.STRING)
	@Column(name = "billing_standard_status", nullable = false)
	@NotNull
	private BillingStandardStatus status;

	@Comment("청구 타입 [정기 | 추가]")
	@Enumerated(EnumType.STRING)
	@Column(name = "billing_standard_type", nullable = false)
	@NotNull
	private BillingType type;

	@Comment("청구 생성 기반이된 계약")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contract_id", nullable = false, updatable = false)
	@NotNull
	private Contract contract;

	@Comment("청구의 약정일")
	@Column(name = "billing_standard_contract_date", nullable = false)
	private LocalDate contractDate;

}
