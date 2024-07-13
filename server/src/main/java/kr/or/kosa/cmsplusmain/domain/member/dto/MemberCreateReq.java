package kr.or.kosa.cmsplusmain.domain.member.dto;

import kr.or.kosa.cmsplusmain.domain.contract.dto.ContractProductReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentInfoReq;

public class MemberCreateReq {
    private MemberInfoReq memberInfoReq; // 회원정보
    private ContractProductReq contractProductReq; // 계약상품정보
    private PaymentInfoReq paymentInfoReq; // 결제정보
}
