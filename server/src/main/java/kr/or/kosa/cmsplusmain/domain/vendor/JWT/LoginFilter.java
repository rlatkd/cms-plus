package kr.or.kosa.cmsplusmain.domain.vendor.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.CustomUserDetailsDto;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.LoginDto;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.TokenRes;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    {
        setFilterProcessesUrl("/api/v1/vendor/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginDto loginDto;
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        }catch (IOException e){
            throw new RuntimeException("Invaild login request format",e);
        }

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException{
        CustomUserDetailsDto customUserDetails = (CustomUserDetailsDto) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        // JWT 토큰 생성
        String token = jwtUtil.createJwt(username, role, 60*60*10L);

        // 응답 본문 작성
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // JSON 응답 보문 작성
        TokenRes tokenRes = TokenRes.builder()
                .accessToken(token)
                .role(role.replace("ROLE_",""))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), tokenRes);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
