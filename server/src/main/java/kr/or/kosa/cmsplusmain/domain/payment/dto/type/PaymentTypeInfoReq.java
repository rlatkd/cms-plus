package kr.or.kosa.cmsplusmain.domain.payment.dto.type;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.EXISTING_PROPERTY,
	property = "paymentType",
	visible = true
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = AutoTypeReq.class, name = PaymentType.Const.AUTO),
	@JsonSubTypes.Type(value = BuyerTypeReq.class, name = PaymentType.Const.BUYER),
	@JsonSubTypes.Type(value = VirtualAccountTypeReq.class, name = PaymentType.Const.VIRTUAL)
})
@Getter
@RequiredArgsConstructor
public abstract class PaymentTypeInfoReq {
	private final PaymentType paymentType;
}
