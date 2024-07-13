package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
public class BuyerTypeRes extends PaymentTypeInfoRes {
	private final Set<PaymentMethod> availableMethods;

	@Builder
	public BuyerTypeRes(Set<PaymentMethod> availableMethods) {
		super(PaymentType.BUYER);
		this.availableMethods = availableMethods;
	}
}
