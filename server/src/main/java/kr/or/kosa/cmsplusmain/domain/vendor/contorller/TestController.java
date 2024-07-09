package kr.or.kosa.cmsplusmain.domain.vendor.contorller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

	@GetMapping("/vendor")
	public String Vendor() {
		return "Vendor";
	}

	@GetMapping("/member")
	public String Member() {
		return "member";
	}
}
