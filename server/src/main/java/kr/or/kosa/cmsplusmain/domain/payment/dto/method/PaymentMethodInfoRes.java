package kr.or.kosa.cmsplusmain.domain.payment.dto.method;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
import lombok.Getter;

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.EXISTING_PROPERTY,
	property = "paymentMethod",
	visible = true
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = CardMethodRes.class, name = PaymentMethod.Const.CARD),
	@JsonSubTypes.Type(value = CMSMethodRes.class, name = PaymentMethod.Const.CMS)
})
@Getter
public abstract class PaymentMethodInfoRes {
	private PaymentMethod paymentMethod;
}
