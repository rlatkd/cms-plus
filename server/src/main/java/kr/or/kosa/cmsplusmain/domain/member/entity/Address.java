package kr.or.kosa.cmsplusmain.domain.member.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Address {
    private String zipcode;
    private String address;
    private String addressDetail;
}
