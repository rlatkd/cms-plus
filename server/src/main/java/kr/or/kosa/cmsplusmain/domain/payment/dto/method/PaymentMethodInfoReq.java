package kr.or.kosa.cmsplusmain.domain.payment.dto.method;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.EXISTING_PROPERTY,
	property = "paymentMethod",
	visible = true
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = CardMethodReq.class, name = PaymentMethod.Const.CARD),
	@JsonSubTypes.Type(value = CMSMethodReq.class, name = PaymentMethod.Const.CMS)
})
@Getter
@RequiredArgsConstructor
public abstract class PaymentMethodInfoReq {
	private final PaymentMethod paymentMethod;
}
