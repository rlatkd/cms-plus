package kr.or.kosa.cmsplusmain.domain.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@RequiredArgsConstructor
//@ConfigurationProperties("setting.kafka") // yml 값 가져와서 해당 클래스의 각 값에 매핑
public class SetProperties {

    private final List<String> bootstrapServers;
    private final String topic1;
    private final int partition;
    private final short replication;

}
