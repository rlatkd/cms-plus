package kr.or.kosa.cmsplusmain.domain.member.controller;

import jakarta.validation.Valid;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.contract.dto.MemberContractListItemDto;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberCreateReq;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDetail;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberListItemRes;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberSearchReq;
import kr.or.kosa.cmsplusmain.domain.member.service.MemberService;
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

    /*
     * 회원 목록 조회
     * */
    @GetMapping("/members")
    public PageRes<MemberListItemRes> getMemberList(@AuthenticationPrincipal VendorUserDetailsDto userDetails, MemberSearchReq memberSearch, PageReq pageable) {
        Long vendorId = 1L;
        return memberService.searchMembers(vendorId, memberSearch, pageable);
    }

    /*
     * 회원 상세 - 기본 정보 조회
     * */
    @GetMapping("/members/{memberId}")
    public MemberDetail getMemberContractList(@AuthenticationPrincipal VendorUserDetailsDto userDetails, @PathVariable Long memberId) {
        Long vendorId = 1L;
        return memberService.findMemberDetailById(vendorId, memberId);
    }

    /*
     * 회원 상세 - 계약 리스트 조회
     * */
    @GetMapping("/members/contracts/{memberId}")
    public SortPageDto.Res<MemberContractListItemDto> getMemberContractList(@AuthenticationPrincipal VendorUserDetailsDto userDetails, @PathVariable Long memberId , PageReq pageable) {
        Long vendorId = 1L;
        return memberService.findContractListItemByMemberId(vendorId, memberId, pageable);
    }

    /*
     * 회원 목록 조회
     * */
    @PostMapping("/members")
    public void createMember(@RequestBody @Valid MemberCreateReq memberCreateReq) {
        Long vendorId = 1L;
        memberService.createMember(vendorId, memberCreateReq);
    }



}
