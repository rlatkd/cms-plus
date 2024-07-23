package kr.or.kosa.cmsplusmain.domain.vendor.dto.Identifier;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.validator.Username;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdFindRes {

    @NotNull
    @Username
    private final String usernmae;
}
