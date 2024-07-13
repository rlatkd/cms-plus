package kr.or.kosa.cmsplusmain.domain.payment.entity.method;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 결제 수단 정보
 *
 * 가상계좌: ...
 * 납부자결제: ...
 * 카드: 카드 번호, 카드 소유주 ...
 * CMS: 계좌 번호, 계좌 소유주 ...
 * */
@Comment("결제수단 세부정보")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PaymentMethodInfo extends BaseEntity {
	@Id
	@GeneratedValue
	@Column(name = "payment_method_info_id")
	private Long id;
}
