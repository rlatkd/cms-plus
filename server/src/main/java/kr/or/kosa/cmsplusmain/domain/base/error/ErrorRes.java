package kr.or.kosa.cmsplusmain.domain.base.error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import kr.or.kosa.cmsplusmain.domain.base.error.exception.BusinessException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorRes {

	private String message;
	private int status;
	private List<FieldError> errors;
	private String code;


	private ErrorRes(final ErrorCode code, final List<FieldError> errors) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.errors = errors;
		this.code = code.getCode();
	}

	private ErrorRes(final ErrorCode code) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.code = code.getCode();
		this.errors = new ArrayList<>();
	}

	private ErrorRes(final ErrorCode code, final String message) {
		this.message = message;
		this.status = code.getStatus();
		this.code = code.getCode();
		this.errors = new ArrayList<>();
	}


	public static ErrorRes of(final ErrorCode code, final BindingResult bindingResult) {
		return new ErrorRes(code, FieldError.of(bindingResult));
	}

	public static ErrorRes of(final ErrorCode code) {
		return new ErrorRes(code);
	}

	public static ErrorRes of(final ErrorCode code, final List<FieldError> errors) {
		return new ErrorRes(code, errors);
	}

	public static ErrorRes of(final BusinessException e) {
		return new ErrorRes(e.getErrorCode(), e.getMessage());
	}

	public static ErrorRes of(MethodArgumentTypeMismatchException e) {
		final String value = e.getValue() == null ? "" : e.getValue().toString();
		final List<ErrorRes.FieldError> errors = ErrorRes.FieldError.of(e.getName(), value, e.getErrorCode());
		return new ErrorRes(ErrorCode.INVALID_TYPE_VALUE, errors);
	}

	public static ErrorRes of(HttpMessageNotReadableException e) {
		if (e.getCause() instanceof MismatchedInputException mismatchedInputException) {
			final String field = mismatchedInputException.getPath().get(0).getFieldName();
			final List<ErrorRes.FieldError> errors = ErrorRes.FieldError.of(field, "", "잘못된 값 입력");
			return new ErrorRes(ErrorCode.INVALID_TYPE_VALUE, errors);
		}
		return new ErrorRes(ErrorCode.INVALID_TYPE_VALUE);
	}


	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {
		private String field;
		private String value;
		private String reason;

		private FieldError(final String field, final String value, final String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		public static List<FieldError> of(final String field, final String value, final String reason) {
			List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, reason));
			return fieldErrors;
		}

		private static List<FieldError> of(final BindingResult bindingResult) {
			final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
			return fieldErrors.stream()
				.map(error -> new FieldError(
					error.getField(),
					error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
					error.getDefaultMessage()))
				.collect(Collectors.toList());
		}
	}


}
