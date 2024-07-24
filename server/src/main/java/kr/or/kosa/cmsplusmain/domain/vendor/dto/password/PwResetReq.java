package kr.or.kosa.cmsplusmain.domain.vendor.dto.password;

import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.validator.Username;
import kr.or.kosa.cmsplusmain.domain.vendor.validator.Password;
import lombok.Getter;

@Getter
public class PwResetReq {

    @NotNull
    @Username
    private final String username;

    @NotNull
    @Password
    private final String newPassword;

    @NotNull
    @Password
    private final String newPasswordCheck;

    public PwResetReq(String username, String newPassword, String newPasswordCheck) {
        this.username = username;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }
}
