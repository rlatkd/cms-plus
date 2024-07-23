package kr.or.kosa.cmsplusmain.domain.payment.entity.type;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusmain.domain.payment.converter.PaymentTypeConverter;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Comment("결제방식 정보")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "payment_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PaymentTypeInfo extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_type_info_id")
	private Long id;

	@Comment("결제방식")
	@Convert(converter = PaymentTypeConverter.class)
	@Column(name = "payment_type", updatable = false, insertable = false)
	private PaymentType paymentType;

	/*
	* 결제방식 정보 삭제
	* */
	@Override
	public void delete(){
		// TODO
		/*
		 * 결제 방식 정보 삭제에서 예외 케이스 있는지 고려
		 * 자동결제, 납부자결제, 가상계좌
		 * */
		super.delete();
	}
}
