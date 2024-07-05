package kr.or.kosa.cmsplusmain.domain.payment.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Comment("결제정보")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "payment_type")    // 결제방식
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Payment extends BaseEntity {

	@Id
	@Column(name = "payment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Comment("결제정보 상태")
	@Column(name = "payment_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@Comment("동의상태")
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_consent_status", nullable = false)
	private ConsentStatus consentStatus;

	@Comment("결제방식")
	@Column(name = "payment_type", insertable = false, updatable = false)
	private String paymentType;

	@Comment("결제수단")
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;

}
