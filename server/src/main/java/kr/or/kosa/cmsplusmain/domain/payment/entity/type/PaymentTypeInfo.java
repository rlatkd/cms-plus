package kr.or.kosa.cmsplusmain.domain.payment.entity.type;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Comment("결제방식 정보")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PaymentTypeInfo extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_type_info_id")
	private Long id;
}
