package kr.or.kosa.cmsplusmain.domain.billing.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusmain.domain.billing.exception.AlreadySentInvoiceException;
import kr.or.kosa.cmsplusmain.domain.billing.exception.DeleteBillingException;
import kr.or.kosa.cmsplusmain.domain.billing.exception.EmptyBillingProductException;
import kr.or.kosa.cmsplusmain.domain.billing.exception.UpdateBillingDateException;
import kr.or.kosa.cmsplusmain.domain.billing.validator.InvoiceMessage;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Comment("청구 (매 달 새로 쌓이는 정보)")
@Entity
@Table(name = "billing")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Billing extends BaseEntity {

	// 최소 청구상품 개수
	private static final int MIN_BILLING_PRODUCT_NUMBER = 1;

	@Id
	@Column(name = "billing_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("청구 기준 기반이된 계약")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "contract_id")
	@NotNull
	private Contract contract;

	@Comment("청구 타입 [정기 or 추가]")
	@Enumerated(EnumType.STRING)
	@Column(name = "billing_type", nullable = false)
	@NotNull
	private BillingType billingType;

	@Comment("청구의 약정일 (청구 생성시 설정한 결제일 != 계약의 약정일과 다를 수 있다.)")
	@Column(name = "billing_contract_day")
	private int contractDay;

	@Comment("결제일 (= 약정일, 납부 시작 및 종료 기간[납부기간은 하루이다.])")
	@Column(name = "billing_date", nullable = false)
	@NotNull
	private LocalDate billingDate;

	@Comment("청구 상태")
	@Enumerated(EnumType.STRING)
	@Column(name = "billing_status", nullable = false)
	@NotNull
	private BillingStatus billingStatus = BillingStatus.CREATED;

	@Comment("청구서 메시지")
	@Column(name = "billing_invoice_message", length = 2000)
	@InvoiceMessage
	@Setter
	private String invoiceMessage;

	/* 청구 상품 목록 */
	@OneToMany(mappedBy = "billing", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@SQLRestriction(BaseEntity.NON_DELETED_QUERY)
	private List<BillingProduct> billingProducts = new ArrayList<>();

	public Billing(Contract contract, BillingType billingType, LocalDate billingDate, int contractDay, List<BillingProduct> billingProducts) {
		// 청구는 최소 한 개의 상품을 가져야한다.
		if (billingProducts.isEmpty()) {
			throw new EmptyBillingProductException();
		}

		this.contract = contract;
		this.billingType = billingType;
		this.contractDay = contractDay;
		this.billingDate = billingDate;

		billingProducts.forEach(this::addBillingProduct);
	}

	/*
	 * 청구 상품 추가
	 * */
	public void addBillingProduct(BillingProduct billingProduct) {
		billingProduct.setBilling(this);
		billingProducts.add(billingProduct);
	}

	/*
	 * 청구 상품 삭제
	 * */
	public void removeBillingProduct(BillingProduct billingProduct) {
		// 청구는 최소 한 개 이상의 상품을 가져야한다.
		if (billingProducts.size() == MIN_BILLING_PRODUCT_NUMBER) {
			throw new EmptyBillingProductException();
		}
		billingProduct.delete();
		billingProducts.remove(billingProduct);
	}

	/*
	 * 청구금액
	 * */
	public long getBillingPrice() {
		return billingProducts.stream()
			.mapToLong(BillingProduct::getBillingProductPrice)
			.sum();
	}

	/*
	* 청구서명
	*
	* 결제일 기준으로
	* YYYY년 MM월 청구서 형식으로 자동생성
	* */
	public String getInvoiceName() {
		String year = Integer.toString(billingDate.getYear());
		String month = Integer.toString(billingDate.getMonthValue());
		return "%s년 %s월 청구서".formatted(year, month);
	}

	/*
	* 청구 결제일 수정
	* */
	public void setBillingDate(LocalDate billingDate) {
		// 청구의 결제일은 청구서 발송 전 상태에서만 수정 가능하다.
		if (!billingStatus.equals(BillingStatus.CREATED)) {
			throw new UpdateBillingDateException();
		}

		this.billingDate = billingDate;
	}

	/*
	* 청구서 발송
	*
	* 청구서 발송 가능 상태 확인 및 상태 변경
	* */
	public void sendInvoice() {
		if (billingStatus != BillingStatus.CREATED) {
			throw new AlreadySentInvoiceException();
		}
		billingStatus = BillingStatus.WAITING_PAYMENT;
	}

	/*
	* 청구 삭제
	*
	* 청구 상품도 같이 삭제한다.
	* */
	@Override
	public void delete() {
		// 청구상태에 따른 삭제 조건 존재
		// 청구상태가 생성 (청구서 발송 전)에만 삭제가 가능하다.
		if (billingStatus != BillingStatus.CREATED) {
			throw new DeleteBillingException();
		}
		super.delete();
		billingProducts.forEach(BaseEntity::delete);
	}
}
