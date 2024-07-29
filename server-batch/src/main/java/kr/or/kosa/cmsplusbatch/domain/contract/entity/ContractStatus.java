package kr.or.kosa.cmsplusbatch.domain.contract.entity;

import kr.or.kosa.cmsplusbatch.domain.base.entity.BaseEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ContractStatus implements BaseEnum {
    ENABLED("진행중"), DISABLED("계약종료");

    private final String title;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }
}
