package kr.or.kosa.cmsplusmessaging.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfraTestController {

    @GetMapping("/infra-test")
    public String infraTest() {
        return "infra-test 222";
    }

}
