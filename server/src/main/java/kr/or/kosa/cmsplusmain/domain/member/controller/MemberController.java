package kr.or.kosa.cmsplusmain.domain.member.controller;

import java.time.Instant;
import java.util.List;

import kr.or.kosa.cmsplusmain.LogExecutionTime;
import kr.or.kosa.cmsplusmain.domain.member.dto.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.base.security.VendorId;
import kr.or.kosa.cmsplusmain.domain.contract.dto.response.MemberContractListItemRes;
import kr.or.kosa.cmsplusmain.domain.excel.dto.ExcelErrorRes;
import kr.or.kosa.cmsplusmain.domain.excel.service.ExcelHandler;
import kr.or.kosa.cmsplusmain.domain.member.service.MemberService;
import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentUpdateReq;
import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.VendorUserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/vendor/management")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PaymentService paymentService;
    private final ExcelHandler<MemberExcelDto> excelHandler = new ExcelHandler<>();

    /**
     * 회원 목록 조회
     * */
    @LogExecutionTime("회원 목록 조회 v1")
    @GetMapping("/members")
    public PageRes<MemberListItemRes> getMemberList(@VendorId Long vendorId, MemberSearchReq memberSearch, PageReq pageable) {
        return memberService.searchMembers(vendorId, memberSearch, pageable);
    }

    /**
     * 회원 기본정보 목록 조회
     * */
    @LogExecutionTime("회원 기본정보 목록 조회 v1")
    @GetMapping("/basicinfo/members")
    public PageRes<MemberDto> getMemberBasicInfoList(@VendorId Long vendorId, MemberSearchReq memberSearch, PageReq pageable) {
        return memberService.findAllMemberBasicInfo(vendorId, memberSearch, pageable);
    }

    /**
     * 회원 상세 - 기본 정보 조회
     * */
    @LogExecutionTime("회원 상세 - 기본 정보 조회 v1")
    @GetMapping("/members/{memberId}")
    public MemberDetail getMemberDetail(@VendorId Long vendorId, @PathVariable Long memberId) {
       return memberService.findMemberDetailById(vendorId, memberId);
    }

    /**
     * 회원 상세 - 계약 리스트 조회
     * */
    @LogExecutionTime("회원 상세 - 계약 리스트 조회 v1")
    @GetMapping("/members/contracts/{memberId}")
    public PageRes<MemberContractListItemRes> getMemberContractList(@VendorId Long vendorId, @PathVariable Long memberId , PageReq pageable) {
        return memberService.findContractListItemByMemberId(vendorId, memberId, pageable);
    }

    /**
     * 회원 등록
     * */
    @LogExecutionTime("회원 등록 v1")
    @PostMapping("/members")
    public Long createMember(@VendorId Long vendorId, @RequestBody @Valid MemberCreateReq memberCreateReq) {
        return memberService.createMember(vendorId, memberCreateReq);
    }

    /**
     * 회원 등록 - 기본 정보
     */
    @PostMapping("/members/basic")
    public void createMemberBaisc(@VendorId Long vendorId, @RequestBody @Valid MemberBasicCreateReq memberBasicCreateReq) {
        return memberService.createMemberBasic(vendorId, memberBasicCreateReq);
    }


    /**
     * 회원 수정 - 기본 정보
     * */
    @LogExecutionTime("회원 수정 - 기본 정보 v1")
    @PutMapping("/members/{memberId}")
    public void updateMember(@VendorId Long vendorId , @RequestBody @Valid MemberUpdateReq memberUpdateReq, @PathVariable Long memberId) {
        memberService.updateMember(vendorId, memberId, memberUpdateReq);
    }

    /**
     * 회원 수정 - 청구 정보
     * */
    @LogExecutionTime("회원 수정 - 청구 정보 v1")
    @PutMapping("/members/billing/{memberId}")
    public void updateMemberBilling(@VendorId Long vendorId , @RequestBody @Valid MemberBillingUpdateReq memberBillingUpdateReq, @PathVariable Long memberId) {
        memberService.updateMemberBilling(vendorId, memberId, memberBillingUpdateReq);
    }

    /**
     * 회원 수정 - 결제 정보
     * */
    @LogExecutionTime("회원 수정 - 결제 정보 v1")
    @PutMapping("/members/payment/{contractId}")
    public void updateMemberPayment(@VendorId Long vendorId , @RequestBody @Valid PaymentUpdateReq paymentUpdateReq, @PathVariable Long contractId) {
        paymentService.updatePayment(vendorId, contractId, paymentUpdateReq);
    }

    /**
     * 회원 삭제
     * */
    @LogExecutionTime("회원 삭제 v1")
    @DeleteMapping("/members/{memberId}")
    public void deleteMember(@VendorId Long vendorId ,  @PathVariable Long memberId) {
        memberService.deleteMember(vendorId, memberId);
    }

    /**
     * 회원 삭제 - 회원의 진행중인 청구 개수
     * */
    @LogExecutionTime("회원 삭제 - 회원의 진행중인 청구 개수 v1")
    @GetMapping("/members/{memberId}/billing")
    public int getInProgressBillingCount(@VendorId Long vendorId , @PathVariable Long memberId) {
        return memberService.countAllInProgressBillingByMember(vendorId, memberId);
    }

    /**
     * 회원 엑셀 -> json 변환
     * */
    @LogExecutionTime("회원 엑셀 -> json 변환 v1")
    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<MemberExcelDto> conventMembersByExcel(MultipartFile file) {
        return excelHandler.handleExcelUpload(file, MemberExcelDto.class);
    }

    /**
    * 회원 대량 등록
    *
    * 실패 목록 리턴
    * */
    @LogExecutionTime("회원 대량 등록 v1")
    @PostMapping(value = "/upload")
    public List<ExcelErrorRes<MemberExcelDto>> saveMembersByExcel(@RequestBody List<MemberExcelDto> memberExcelList) {
        Long vendorId = 1L;
        return memberService.uploadMembersByExcel(vendorId, memberExcelList);
    }


}
