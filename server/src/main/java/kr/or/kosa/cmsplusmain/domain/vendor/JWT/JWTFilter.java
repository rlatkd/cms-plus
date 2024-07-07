package kr.or.kosa.cmsplusmain.domain.vendor.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.CustomUserDetailsDto;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.SignupDto;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.UserRole;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        System.out.println("===================authorization : " + authorization);

        // Authorization 헤더 검증
        if(authorization == null || !authorization.startsWith("Bearer ")){
            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)){
            System.out.println("token expired");
            filterChain.doFilter(request, response);
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        Vendor vendor = Vendor.builder()
                .username(username)
                .role(UserRole.valueOf(role))
                .build();

        CustomUserDetailsDto customUserDetailsDto = new CustomUserDetailsDto(vendor);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetailsDto, null, customUserDetailsDto.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
