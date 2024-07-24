package kr.or.kosa.cmsplusmain.domain.base.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor
@Getter
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
	METHOD_NOT_ALLOWED(405, "C002", "Method Not Allowed"),
	ENTITY_NOT_FOUND(400, "C003", "Entity Not Found"),
	INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
	INVALID_TYPE_VALUE(400, "C005", "Invalid Type Value"),
	HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

	// Member
	EMAIL_DUPLICATION(400, "M001", "Email is Duplicate"),
	LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),
	MEMBER_NOT_FOUND(404, "M003", "Member Not Found"),

	// Vendor
	VENDOR_NOT_FOUND(404, "V001", "Vendor Not Found"),
	VENDOR_USERNAME_DUPLICATION(400, "V002", "Vendor Username is Duplicate"),

	// Billing
	BILLING_NOT_FOUND(404, "B001", "Billing Not Found"),
	INVALID_BILLING_STATUS(400, "B002", "Invalid Billing Status"),
	INVALID_UPDATE_BILLING(400, "B003", "Invalid Billing Update"),
	EMPTY_BILLING_PRODUCT(400, "B004", "Empty Billing Product"),

	// Contract
	CONTRACT_NOT_FOUND(404, "CT001", "Contract Not Found"),
	EMPTY_CONTRACT_PRODUCT(400, "CT002", "Empty Contract Product"),

	// Product
	PRODUCT_NOT_FOUND(404, "P001", "Product Not Found"),

	// Payment
	PAYMENT_NOT_FOUND(404, "PM001", "Payment Not Found"),
	INVALID_PAYMENT_METHOD(400, "PM002", "Invalid Payment Method"),
	INVALID_PAYMENT_TYPE(400, "PM003", "Invalid Payment Type"),

	// Settings
	INVALID_SIMP_CONSENT_SETTING(400, "S001", "Invalid Simple Consent Setting");

	private final int status;
	private final String code;
	private final String message;
}