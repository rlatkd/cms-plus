package kr.or.kosa.cmsplusmain.domain.member.controller;

import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberListItem;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.member.service.MemberService;
import kr.or.kosa.cmsplusmain.domain.vendor.dto.VendorUserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendor/management")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public SortPageDto.Res<MemberListItem> getMemberList(@AuthenticationPrincipal VendorUserDetailsDto userDetails, SortPageDto.Req pageable) {
//        String username = userDetails.getUsername();
        String username = "vendor1";
        return memberService.findMemberListItem(username, pageable);
    }
}
