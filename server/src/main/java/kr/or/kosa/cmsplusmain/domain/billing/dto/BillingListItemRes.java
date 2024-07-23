package kr.or.kosa.cmsplusmain.domain.billing.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.contract.entity.Contract;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BillingListItemRes {
	private final Long billingId;
	private final String memberName;
	private final String memberPhone;
	@Setter
	private List<BillingProductRes> billingProducts;
	private final Long billingPrice;
	private final BillingStatus billingStatus;
	private final PaymentType paymentType;
	private final LocalDate billingDate;

	@Builder
	public BillingListItemRes(Long billingId, String memberName, String memberPhone,
		List<BillingProductRes> billingProducts, Long billingPrice, BillingStatus billingStatus,
		PaymentType paymentType,
		LocalDate billingDate) {
		this.billingId = billingId;
		this.memberName = memberName;
		this.memberPhone = memberPhone;
		this.billingProducts = billingProducts;
		this.billingPrice = billingPrice;
		this.billingStatus = billingStatus;
		this.paymentType = paymentType;
		this.billingDate = billingDate;
	}

	@QueryProjection
	public BillingListItemRes(Long billingId, String memberName, String memberPhone,
		Long billingPrice, BillingStatus billingStatus,
		PaymentType paymentType, LocalDate billingDate) {
		this.billingId = billingId;
		this.memberName = memberName;
		this.memberPhone = memberPhone;
		this.billingPrice = billingPrice;
		this.billingStatus = billingStatus;
		this.paymentType = paymentType;
		this.billingDate = billingDate;
		this.billingProducts = new ArrayList<>();
	}

	public static BillingListItemRes fromEntity(Billing billing) {

		// NOT NULL
		final Contract contract = billing.getContract();
		final Member member = contract.getMember();
		final Payment payment = contract.getPayment();

		final List<BillingProductRes> billingProductResList = billing.getBillingProducts()
			.stream()
			.map(BillingProductRes::fromEntity)
			.toList();

		return BillingListItemRes.builder()
			.billingId(billing.getId())
			.memberName(member.getName())
			.memberPhone(member.getPhone())
			.billingProducts(billingProductResList)
			.billingPrice(billing.getBillingPrice())
			.billingStatus(billing.getBillingStatus())
			.paymentType(payment.getPaymentType())
			.billingDate(billing.getBillingDate())
			.build();
	}

}
