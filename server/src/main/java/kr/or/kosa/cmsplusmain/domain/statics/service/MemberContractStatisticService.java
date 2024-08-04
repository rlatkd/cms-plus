package kr.or.kosa.cmsplusmain.domain.statics.service;

import kr.or.kosa.cmsplusmain.domain.statics.dto.MemberContractStatisticDto;
import kr.or.kosa.cmsplusmain.domain.statics.repository.MemberContractStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberContractStatisticService {

    private final MemberContractStatisticRepository memberContractStatisticRepository;

    public List<MemberContractStatisticDto> getMemberContractStatistics(Long vendorId) {
        return memberContractStatisticRepository.findMemberContractStatistic(vendorId);
    }

}