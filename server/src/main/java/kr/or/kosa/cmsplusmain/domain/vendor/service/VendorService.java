package kr.or.kosa.cmsplusmain.domain.vendor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.kosa.cmsplusmain.domain.vendor.JWT.JWTUtil;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.RefreshTokenRes;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.SignupDto;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.UserRole;
import kr.or.kosa.cmsplusmain.domain.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendorService {
    private final VendorRepository vendorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void join(SignupDto signupDto) {
        String username = signupDto.getUsername();
        String password = bCryptPasswordEncoder.encode(signupDto.getPassword());
        UserRole role = UserRole.ROLE_VENDOR;

        boolean isExist = vendorRepository.isExistUsername(username);
        if(isExist){
            throw new RuntimeException("Username already exists.");
        }

        vendorRepository.save(signupDto.toEntity(username, password, role));
    }

    @Transactional
    public ResponseEntity<?> refresh (HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationRefresh = request.getHeader("Authorization-refresh");

        // authorizationRefresh 헤더 검증
        if(authorizationRefresh == null || !authorizationRefresh.startsWith("Bearer ")){
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }


        String refreshToken = authorizationRefresh.split(" ")[1];

        // 토큰 만료 여부 확인
        try{
            jwtUtil.isExpired(refreshToken);
        }catch(ExpiredJwtException e){

            // response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refreshToken);

        // 토큰이 refresh인지 확인
        if (!category.equals("refresh")){
            // response status code
            return new ResponseEntity<>("invaild refresh token", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 Redis에 저장되어 있는지 확인

        String storedRefreshToken = redisTemplate.opsForValue().get(jwtUtil.getUsername(refreshToken));
        if(storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)){
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // JWT 토큰 생성
        String newAccessToken = jwtUtil.createJwt("access",username,role,10 * 60 * 1000L);
        String newRefreshToken = jwtUtil.createJwt("refresh",username, role, 24 * 60 * 60 * 1000L);

        // 기존 토큰을 Redis에서 제거 후 새로운 토큰 저장
        redisTemplate.delete(username);
        redisTemplate.opsForValue().set(username, newRefreshToken, 14, TimeUnit.DAYS);

        // 응답 본문 작성
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Json 응답 본문 작성
        RefreshTokenRes refreshTokenRes = RefreshTokenRes.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), refreshTokenRes);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    public boolean isExistUsername(String username) {
        return vendorRepository.isExistUsername(username);
    }
}
