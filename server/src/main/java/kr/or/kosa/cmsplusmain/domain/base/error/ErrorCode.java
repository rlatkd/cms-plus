package kr.or.kosa.cmsplusmain.domain.base.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
@Getter
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(400, "C001", "유효하지 않은 입력값입니다"),
	METHOD_NOT_ALLOWED(405, "C002", "허용되지 않은 메소드입니다"),
	ENTITY_NOT_FOUND(400, "C003", "엔티티를 찾을 수 없습니다"),
	INTERNAL_SERVER_ERROR(500, "C004", "서버 오류가 발생했습니다"),
	INVALID_TYPE_VALUE(400, "C005", "유효하지 않은 타입 값입니다"),
	HANDLE_ACCESS_DENIED(403, "C006", "접근이 거부되었습니다"),

	// Member
	EMAIL_DUPLICATION(400, "M001", "이메일이 중복되었습니다"),
	LOGIN_INPUT_INVALID(400, "M002", "로그인 입력이 유효하지 않습니다"),
	MEMBER_NOT_FOUND(404, "M003", "회원을 찾을 수 없습니다"),

	// Vendor
	VENDOR_NOT_FOUND(404, "V001", "판매자를 찾을 수 없습니다"),
	VENDOR_USERNAME_DUPLICATION(400, "V002", "판매자 사용자명이 중복되었습니다"),

	// Billing
	BILLING_NOT_FOUND(404, "B001", "청구를 찾을 수 없습니다"),
	INVALID_BILLING_STATUS(400, "B002", "유효하지 않은 청구 상태입니다"),
	INVALID_UPDATE_BILLING(400, "B003", "유효하지 않은 청구 업데이트입니다"),
	EMPTY_BILLING_PRODUCT(400, "B004", "청구 상품이 비어있습니다"),

	// Contract
	CONTRACT_NOT_FOUND(404, "CT001", "계약을 찾을 수 없습니다"),
	EMPTY_CONTRACT_PRODUCT(400, "CT002", "계약 상품이 비어있습니다"),

	// Product
	PRODUCT_NOT_FOUND(404, "P001", "상품을 찾을 수 없습니다"),

	// Payment
	PAYMENT_NOT_FOUND(404, "PM001", "결제를 찾을 수 없습니다"),
	INVALID_PAYMENT_METHOD(400, "PM002", "유효하지 않은 결제수단입니다"),
	INVALID_PAYMENT_TYPE(400, "PM003", "유효하지 않은 결제방식입니다"),

	// Settings
	INVALID_SIMP_CONSENT_SETTING(400, "S001", "유효하지 않은 간편 동의 설정입니다");

	private final int status;
	private final String code;
	private final String message;
}