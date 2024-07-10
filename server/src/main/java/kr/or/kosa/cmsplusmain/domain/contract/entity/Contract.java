package kr.or.kosa.cmsplusmain.domain.contract.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusmain.domain.contract.validator.ContractName;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Comment("회원과 고객간의 계약 (학원 - 학생 간 계약)")
@Table(name = "contract")
@Entity
@Getter
@Builder
@ToString(exclude = {"vendor"})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract extends BaseEntity {

	@Id
	@Column(name = "contract_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("계약한 회원의 고객")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vendor_id")
	@NotNull
	private Vendor vendor;

	@Comment("계약한 회원")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id")
	@NotNull
	private Member member;

	@Comment("계약 상태")
	@Column(name = "contract_status", nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private ContractStatus status = ContractStatus.ENABLED;

	@Comment("계약 이름")
	@Column(name = "contract_name", nullable = false, length = 40)
	@ContractName
	@NotNull
	@Setter
	private String name;

	@Comment("계약 약정일")
	@Column(name = "contract_day", nullable = false)
	@Setter
	private int contractDay;

	@Comment("계약 결제정보")
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "payment_id")
	@NotNull
	private Payment payment;

	@Comment("청구 가능 기간 - 시작일")
	@Column(name = "contract_start_date", nullable = false)
	@NotNull
	private LocalDate contractStartDate;

	@Comment("청구 가능 기간 - 종료일")
	@Column(name = "contract_end_date", nullable = false)
	@NotNull
	private LocalDate contractEndDate;

	/* 계약한 상품 목록 */
	@OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
	@SQLRestriction(BaseEntity.NON_DELETED_QUERY)
	private List<ContractProduct> contractProducts = new ArrayList<>();

	/*
	 * 계약금액
	 * */
	public Long getContractPrice() {
		return contractProducts.stream()
			.mapToLong(ContractProduct::getTotalPrice)
			.sum();
	}

	/*
	 * id만 들고있는 빈 객체
	 * */
	public static Contract of(Long contractId) {
		Contract contract = new Contract();
		contract.id = contractId;
		return contract;
	}
}
