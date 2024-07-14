package kr.or.kosa.cmsplusmain.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import kr.or.kosa.cmsplusmain.domain.base.validator.HomePhone;
import kr.or.kosa.cmsplusmain.domain.base.validator.Memo;
import kr.or.kosa.cmsplusmain.domain.base.validator.PersonName;
import kr.or.kosa.cmsplusmain.domain.base.validator.Phone;
import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractUpdateReq;
import kr.or.kosa.cmsplusmain.domain.messaging.MessageSendMethod;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentCreateReq;

import java.time.LocalDate;

public class MemberCreateReq {

    @NotNull
    @PersonName
    private String memberName; // 회원 이름

    @NotNull
    @Phone
    private String memberPhone; // 회원 휴대전화 번호

    @NotNull
    private LocalDate memberEnrollDate; // 회원 등록 날짜

    @HomePhone
    private String memberHomePhone; // 회원 유선전화 번호

    @NotNull
    @Email
    private String memberEmail; // 회원 이메일

    //TODO
    //은아가 정의한거 확인해보기
    private String memberAddress; // 회원 주소

    @Memo
    private String memberMemo; // 회원 메모

    @NotNull
    private MessageSendMethod invoiceSendMethod; // 청구 전송 방법

    @NotNull
    private boolean autoInvoiceSend; // 자동 청구서 발송

    @NotNull
    private boolean autoBilling; // 자동 청구 발송

    private PaymentCreateReq paymentCreateReq;   // 결제정보

    private ContractUpdateReq contractUpdateReq; // 계약정보
}
