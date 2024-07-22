package kr.or.kosa.cmsplusmain.domain.base.security;

import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.or.kosa.cmsplusmain.domain.vendor.dto.VendorUserDetailsDto;

public class AuthenticatedVendorIdResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(VendorId.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new UnauthenticatedAccessException("사용자 인증이 필요합니다.");
		}

		Object principal = authentication.getPrincipal();
		if (principal instanceof VendorUserDetailsDto) {
			return ((VendorUserDetailsDto) principal).getId();
		}

		throw new UnauthenticatedAccessException("올바른 사용자 정보를 찾을 수 없습니다.");
	}
}
