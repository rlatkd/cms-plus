package kr.or.kosa.cmsplusmain.domain.billing.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import kr.or.kosa.cmsplusmain.domain.billing.exception.EmptyBillingProductException;
import kr.or.kosa.cmsplusmain.domain.billing.exception.InvalidBillingStatusException;
import kr.or.kosa.cmsplusmain.domain.billing.exception.InvalidUpdateBillingException;
import kr.or.kosa.cmsplusmain.domain.billing.validator.InvoiceMessage;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
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
	@Column(name = "billing_contract_day", nullable = false)
	@NotNull
	private Integer contractDay;

	@Comment("결제일 (= 약정일, 납부 시작 및 종료 기간[납부기간은 하루이다.])")
	@Column(name = "billing_date", nullable = false)
	@NotNull
	private LocalDate billingDate;

	@Comment("청구 상태")
	@Enumerated(EnumType.STRING)
	@Column(name = "billing_status", nullable = false)
	@NotNull
	@Setter
	private BillingStatus billingStatus = BillingStatus.CREATED;

	@Comment("청구서 메시지")
	@Column(name = "billing_invoice_message", length = 2000)
	@InvoiceMessage
	@Setter
	private String invoiceMessage;

	@Comment("청구서 보낸시각")
	@Column(name = "billing_invoice_send_datetime")
	@Setter
	private LocalDateTime invoiceSendDateTime;

	@Comment("청구 결제된 시각")
	@Column(name = "billing_paid_datetime")
	@Setter
	private LocalDateTime paidDateTime;

	/* 청구 상품 목록 */
	@OneToMany(mappedBy = "billing", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@SQLRestriction(BaseEntity.NON_DELETED_QUERY)
	private List<BillingProduct> billingProducts = new ArrayList<>();

	public Billing(Contract contract, BillingType billingType, LocalDate billingDate, int contractDay, List<BillingProduct> billingProducts) {
		// 청구는 최소 한 개의 상품을 가져야한다.
		if (billingProducts.isEmpty()) {
			throw new EmptyBillingProductException("청구는 최소 한 개 이상의 상품을 지녀야합니다");
		}

		this.contract = contract;
		this.billingType = billingType;
		this.contractDay = contractDay;
		this.billingDate = billingDate;

		billingProducts.forEach(this::addBillingProduct);
	}

	/**
	 * 청구 상품 추가
	 * */
	public void addBillingProduct(BillingProduct billingProduct) {
		billingProduct.setBilling(this);
		billingProducts.add(billingProduct);
	}

	/**
	 * 청구 상품 삭제 | 청구는 최소 1개의 상품을 지녀야한다.
	 * @throws EmptyBillingProductException 청구상품이 1개인 경우
	 * */
	public void removeBillingProduct(BillingProduct billingProduct) {
		if (billingProducts.size() == MIN_BILLING_PRODUCT_NUMBER) {
			throw new EmptyBillingProductException("청구는 최소 한 개 이상의 상품을 지녀야합니다");
		}
		billingProduct.delete();
		billingProducts.remove(billingProduct);
	}

	/**
	 * 청구금액 | 전체 청구 상품의 수량 * 가격
	 * */
	public long getBillingPrice() {
		return billingProducts.stream()
			.mapToLong(BillingProduct::getBillingProductPrice)
			.sum();
	}

	/**
	* 청구서명
	* @return "YYYY년 MM월 청구서" (결제일 기준)
	* */
	public String getInvoiceName() {
		String year = Integer.toString(billingDate.getYear());
		String month = Integer.toString(billingDate.getMonthValue());
		return "%s년 %s월 청구서".formatted(year, month);
	}

	/**
	* 청구서 발송 가능 상태여부 | 청구서는 최대 1번 [생성, 미납] 상태에서만 발송 가능하다.
	 * @return 청구상태가 [생성, 미납]이 아니거나 청구서가 발송된 적이 있는 경우 false, 나머지 true
	* */
	public boolean canSendInvoice() {
		return (billingStatus == BillingStatus.CREATED
			|| billingStatus == BillingStatus.NON_PAID)
			&& invoiceSendDateTime == null;
	}

	/**
	* 청구서 발송 완료
	 * @throws InvalidBillingStatusException 청구서 발송 불가능인경우
	* */
	public void setInvoiceSent() {
		if (!canSendInvoice()) {
			throw new InvalidBillingStatusException("청구서 발송이 불가능한 상태입니다");
		}
		billingStatus = BillingStatus.WAITING_PAYMENT;
		invoiceSendDateTime = LocalDateTime.now();
	}

	/**
	* 청구소 발송 취소 가능여부 | 청구서는 [수납대기] 상태에서만 취소 가능하다.
 	* @return 청구상태가 [수납대기]이 아닌경우 false, 나머지 true
	* */
	public boolean canCancelInvoice() {
		return billingStatus == BillingStatus.WAITING_PAYMENT;
	}

	/**
	 * 청구서 발송 취소 상태 변경 | 청구생성 상태로 돌아간다.
	 * @throws InvalidBillingStatusException 청구서 발송 취소가 불가능한 경우
	 * */
	public void setInvoiceCancelled() {
		if (!canCancelInvoice()) {
			throw new InvalidBillingStatusException("청구서 발송 취소가 불가능합니다");
		}
		billingStatus = BillingStatus.CREATED;
		invoiceSendDateTime = null;
	}

	/**
	* 청구 결제일 수정 | 청구의 결제일은 청구서 발송 전에만 수정 가능하다.
	 * @throws InvalidUpdateBillingException 청구 결제일 수정 불가
	* */
	public void updateBillingDate(LocalDate billingDate) {
		if (this.billingDate.equals(billingDate)) {
			return;
		}
		if (!billingStatus.equals(BillingStatus.CREATED)) {
			throw new InvalidUpdateBillingException("청구 결제일은 청구서 발송 전에만 수정 가능합니다");
		}

		this.billingDate = billingDate;
	}

	/**
	* 청구 실시간 결제 가능 여부 | 실시간 결제는 생성 및 수납대기 상태에서만 가능하다.
	* */
	public boolean canPayRealtime() {
		if (!(billingStatus == BillingStatus.WAITING_PAYMENT
			|| billingStatus == BillingStatus.CREATED)) {
			return false;
		}

		Payment payment = contract.getPayment();
		return payment.canPayRealtime();
	}

	/**
	* 청구 실시간 결제 완료 상태변경
	 * @throws InvalidBillingStatusException 이미 결제된 청구인 경우
	* */
	public void setPaid() {
		if (billingStatus == BillingStatus.PAID) {
			throw new InvalidBillingStatusException("이미 결제된 청구입니다.");
		}
		billingStatus = BillingStatus.PAID;
		paidDateTime = LocalDateTime.now();
	}

	/**
	* 청구 결제 취소 가능 | 납부 상태 그리고 취소가능 결제수단 여부
	* */
	public boolean canCancelPaid() {
		if (billingStatus != BillingStatus.PAID) {
			return false;
		}

		Payment payment = contract.getPayment();
		return payment.canCancel();
	}

	/**
	 * 청구 결제 취소 상태 변경
	 * @throws InvalidBillingStatusException 결제 취소가 불가능한 경우
	 * */
	public void setPayCanceled() {
		if (!canCancelPaid()) {
			throw new InvalidBillingStatusException("결제 취소가 불가능합니다.");
		}
		billingStatus = BillingStatus.WAITING_PAYMENT;
		paidDateTime = null;
	}

	/**
	* 청구 수정 가능여부 | 생성 및 수납대기 상태에서만 가능
	* */
	public boolean canBeUpdated() {
		return this.billingStatus == BillingStatus.CREATED
			|| billingStatus == BillingStatus.WAITING_PAYMENT;
	}

	/**
	* 청구 삭제 가능여부 | 생성 수납대기 상태에서만 가능
	* */
	public boolean canBeDeleted() {
		return this.billingStatus == BillingStatus.CREATED || billingStatus == BillingStatus.WAITING_PAYMENT;
	}

	/**
	* 청구 삭제 | 청구 상품도 같이 삭제한다.
 	* @throws InvalidBillingStatusException 납부 전에만 삭제가능
	* */
	@Override
	public void delete() {
		if (billingStatus == BillingStatus.CREATED || billingStatus == BillingStatus.WAITING_PAYMENT) {
			throw new InvalidBillingStatusException("청구는 납부 전에만 삭제가 가능합니다");
		}
		super.delete();
		billingProducts.forEach(BaseEntity::delete);
	}
}
