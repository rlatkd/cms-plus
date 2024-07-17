package kr.or.kosa.cmsplusmain.domain.member.controller;

import jakarta.validation.Valid;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.contract.dto.MemberContractListItemRes;
import kr.or.kosa.cmsplusmain.domain.member.dto.*;
import kr.or.kosa.cmsplusmain.domain.member.service.MemberService;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentUpdateReq;
import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.VendorUserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/vendor/management")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PaymentService paymentService;

    /*
     * 회원 목록 조회
     * */
    @GetMapping("/members")
    public PageRes<MemberListItemRes> getMemberList(@AuthenticationPrincipal VendorUserDetailsDto userDetails, MemberSearchReq memberSearch, PageReq pageable) {
        Long vendorId = userDetails.getId();
        return memberService.searchMembers(vendorId, memberSearch, pageable);
    }

    /*
     * 회원 상세 - 기본 정보 조회
     * */
    @GetMapping("/members/{memberId}")
    public MemberDetail getMemberDetail(@AuthenticationPrincipal VendorUserDetailsDto userDetails, @PathVariable Long memberId) {
       Long vendorId = userDetails.getId();
       return memberService.findMemberDetailById(vendorId, memberId);
    }

    /*
     * 회원 상세 - 계약 리스트 조회
     * */
    @GetMapping("/members/contracts/{memberId}")
    public PageRes<MemberContractListItemRes> getMemberContractList(@AuthenticationPrincipal VendorUserDetailsDto userDetails, @PathVariable Long memberId , PageReq pageable) {
        Long vendorId = userDetails.getId();
        return memberService.findContractListItemByMemberId(vendorId, memberId, pageable);
    }

    /*
     * 회원 목록 조회
     * */
    @PostMapping("/members")
    public void createMember(@AuthenticationPrincipal VendorUserDetailsDto userDetails, @RequestBody @Valid MemberCreateReq memberCreateReq) {
        Long vendorId = userDetails.getId();
        memberService.createMember(vendorId, memberCreateReq);
    }

    /*
     * 회원 수정 - 기본 정보
     * */
    @PutMapping("/members/{memberId}")
    public void updateMember(@AuthenticationPrincipal VendorUserDetailsDto userDetails , @RequestBody @Valid MemberUpdateReq memberUpdateReq, @PathVariable Long memberId) {
        Long vendorId = userDetails.getId();
        memberService.updateMember(vendorId,memberId, memberUpdateReq);
    }

    /*
     * 회원 수정 - 청구 정보
     * */
    @PutMapping("/members/billing/{memberId}")
    public void updateMemberBilling(@AuthenticationPrincipal VendorUserDetailsDto userDetails , @RequestBody @Valid MemberBillingUpdateReq memberBillingUpdateReq, @PathVariable Long memberId) {
        Long vendorId = userDetails.getId();
        memberService.updateMemberBilling(vendorId, memberId, memberBillingUpdateReq);
    }

    /*
     * 회원 수정 - 결제 정보
     * */
    @PutMapping("/members/payment/{contractId}")
    public void updateMemberPayment(@AuthenticationPrincipal VendorUserDetailsDto userDetails , @RequestBody @Valid PaymentUpdateReq paymentUpdateReq, @PathVariable Long contractId) {
        Long vendorId = userDetails.getId();
        paymentService.updatePayment(vendorId, contractId, paymentUpdateReq);
    }
}
