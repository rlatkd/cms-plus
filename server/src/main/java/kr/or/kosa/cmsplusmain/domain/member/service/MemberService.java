package kr.or.kosa.cmsplusmain.domain.member.service;

import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberListItem;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberCustomRepository memberCustomRepository;

    /*
     * 회원 목록 조회
     * */
    public SortPageDto.Res<MemberListItem> findMemberListItem (String username, SortPageDto.Req pageable) {

        int countMemberListItem = memberCustomRepository.countAllByVendorUsername(username);

        int totalPages = (int) Math.ceil((double) countMemberListItem / pageable.getSize());

        List<MemberListItem> pagedMemberListItem = memberCustomRepository
                .findPagedByVendorUsername(username, pageable)
                .stream()
                .map(MemberListItem::fromEntity)
                .toList();

        return new SortPageDto.Res<>(totalPages, pagedMemberListItem);
    }
}
