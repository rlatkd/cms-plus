package kr.or.kosa.cmsplusmain.domain.vendor.contorller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.or.kosa.cmsplusmain.domain.vendor.JWT.JWTUtil;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.SignupDto;
import kr.or.kosa.cmsplusmain.domain.vendor.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/vendor/auth")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;
    private final JWTUtil jwtUtil;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid SignupDto signupDto) {
        try {
            vendorService.join(signupDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Signup successful.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/username-check")
    public ResponseEntity<Boolean> isExistUsername(@RequestParam String username) {
        boolean isExist = vendorService.isExistUsername(username);
        return ResponseEntity.ok(isExist);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            return vendorService.refresh(request, response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while refreshing token");
        }
    }
}
