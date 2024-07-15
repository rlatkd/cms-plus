package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.BuyerPaymentType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

//TODO
// 코드 점검 필요

@Getter
public class BuyerTypeReq extends PaymentTypeInfoReq {
	private final Set<PaymentMethod> availableMethods;

	@Builder
	@JsonCreator
	public BuyerTypeReq(@JsonProperty("availableMethods") Set<PaymentMethod> availableMethods) {
		super(PaymentType.BUYER);
		this.availableMethods = availableMethods;
	}

	public BuyerPaymentType toEntity() {
		return BuyerPaymentType.builder()
				.availableMethods(this.availableMethods)
				.build();
	}
}
