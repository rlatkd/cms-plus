package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import java.util.HashSet;
import java.util.Set;

import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BuyerTypeRes extends PaymentTypeInfoRes {
	private Set<PaymentMethod> availableMethods;
}
