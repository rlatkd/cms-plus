package kr.or.kosa.cmsplusbatch.domain.payment.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kr.or.kosa.cmsplusbatch.domain.payment.entity.method.PaymentMethod;


@Converter(autoApply = true)
public class PaymentMethodConverter implements AttributeConverter<PaymentMethod, String> {
	@Override
	public String convertToDatabaseColumn(PaymentMethod attribute) {
		return (attribute == null) ? null : attribute.getCode();
	}

	@Override
	public PaymentMethod convertToEntityAttribute(String dbData) {
		return PaymentMethod.fromCode(dbData);
	}
}
