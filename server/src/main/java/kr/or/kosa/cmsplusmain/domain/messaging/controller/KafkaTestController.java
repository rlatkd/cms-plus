package kr.or.kosa.cmsplusmain.domain.messaging.controller;

import kr.or.kosa.cmsplusmain.domain.messaging.dto.KafkaTestDto;
import kr.or.kosa.cmsplusmain.domain.messaging.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kafkatest")
public class KafkaTestController {

    private final MessageService messageService;

    @PostMapping
    public String kafkaTest(@RequestBody KafkaTestDto kafkaTestDto) {

        messageService.send(kafkaTestDto);

        return "success";

    }


    // phone, email, 기타등등(카톡아이디)

}
