package kr.or.kosa.cmsplusmain.domain.member.controller;

import java.util.List;

import jakarta.validation.Valid;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.MemberContractListItemRes;
import kr.or.kosa.cmsplusmain.domain.excel.dto.ExcelErrorRes;
import kr.or.kosa.cmsplusmain.domain.excel.service.ExcelHandler;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberBillingUpdateReq;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberCreateReq;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDetail;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberExcelDto;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberListItemRes;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberSearchReq;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberUpdateReq;
import kr.or.kosa.cmsplusmain.domain.member.service.MemberService;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentUpdateReq;
import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.VendorUserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/vendor/management")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PaymentService paymentService;
    private final ExcelHandler<MemberExcelDto> excelHandler = new ExcelHandler<>();

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
     * 회원 등록
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
        memberService.updateMember(vendorId, memberId, memberUpdateReq);
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

    /*
     * 회원 삭제
     * */
    @DeleteMapping("/members/{memberId}}")
    public void deleteMember(@AuthenticationPrincipal VendorUserDetailsDto userDetails ,  @PathVariable Long memberId) {
        Long vendorId = userDetails.getId();

    }

    /*
     * 회원 엑셀 -> json 변환
     * */
    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<MemberExcelDto> conventMembersByExcel(MultipartFile file) {
        return excelHandler.handleExcelUpload(file, MemberExcelDto.class);
    }

    /*
    * 회원 대량 등록
    *
    * 실패 목록 리턴
    * */
    @PostMapping(value = "/upload")
    public List<ExcelErrorRes<MemberExcelDto>> saveMembersByExcel(@RequestBody List<MemberExcelDto> memberExcelList) {
        Long vendorId = 1L;
        return memberService.uploadMembersByExcel(vendorId, memberExcelList);
    }
}
