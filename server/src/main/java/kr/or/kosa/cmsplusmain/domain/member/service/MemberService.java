package kr.or.kosa.cmsplusmain.domain.member.service;

import jakarta.persistence.EntityNotFoundException;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.MemberContractListItemDto;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractCustomRepository;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberCreateReq;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDetail;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberListItem;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberCustomRepository;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberRepository;
import kr.or.kosa.cmsplusmain.domain.payment.dto.method.PaymentMethodInfoReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.AutoTypeReq;
import kr.or.kosa.cmsplusmain.domain.payment.dto.type.PaymentTypeInfoReq;
import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
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
    private final MemberRepository memberRepository;
    private final ContractCustomRepository contractCustomRepository;
    private final BillingCustomRepository billingCustomRepository;

    private final PaymentService paymentService;

    /*
     * 회원 목록 조회
     * */
    public SortPageDto.Res<MemberListItem> findMemberListItem (Long vendorId, SortPageDto.Req pageable) {

        int countMemberListItem = memberCustomRepository.countAllMemberByVendor(vendorId);

        int totalPages = (int) Math.ceil((double) countMemberListItem / pageable.getSize());

        List<MemberListItem> memberListItem = memberCustomRepository
                .findAllMemberByVendor(vendorId, pageable)
                .stream()
                .map(MemberListItem::fromEntity)
                .toList();

        return new SortPageDto.Res<>(totalPages, memberListItem);
    }

    /*
    *  회원 상세 - 기본정보
    * */
    public MemberDetail findMemberDetailById(Long vendorId, Long memberId) {

        // 회원 정보 조회
        Member member = memberCustomRepository.findMemberDetailById(vendorId, memberId)
                        .orElseThrow(() -> new EntityNotFoundException("회원 ID 없음(" + memberId + ")"));

        // 청구서 수
        int billingCount = billingCustomRepository.findBillingStandardByMemberId(vendorId, memberId);

        // 총 청구 금액
        Long totalBillingPrice = billingCustomRepository.findBillingProductByMemberId(vendorId, memberId);

        return MemberDetail.fromEntity(member, billingCount, totalBillingPrice);
    }

    /*
    * 회원 상세 - 계약리스트
    * */
    public SortPageDto.Res<MemberContractListItemDto> findContractListItemByMemberId(Long vendorId, Long memberId, PageReq pageable) {

        int countAllContractListItemByMemberId = contractCustomRepository.countContractListItemByMemberId(vendorId, memberId);
        int totalPages = (int) Math.ceil((double) countAllContractListItemByMemberId / pageable.getSize());

        List<MemberContractListItemDto> memberContractListItemDtos = contractCustomRepository
                .findContractListItemByMemberId(vendorId, memberId, pageable)
                .stream()
                .map(MemberContractListItemDto::fromEntity)
                .toList();

        return new SortPageDto.Res<>(totalPages, memberContractListItemDtos);
    }

    /*
     * 회원 등록
     * */
    @Transactional
    public void createMember(Long vendorId, MemberCreateReq memberCreateReq) {

        // 회원 정보를 DB에 저장한다.
        Member member = memberCreateReq.toEntity(vendorId);
        memberRepository.save(member);

        //TODO
        // 회원이 이미 있다면 회원 저장은 한번 더 하지 않는다.


        // 결제 정보를 DB에 저장한다.
        // 결제 방식 정보 ( 자동결제, 납부자결제, 가상계좌 )
        PaymentTypeInfoReq paymentTypeInfoReq = memberCreateReq.getPaymentCreateReq().getPaymentTypeInfoReq();
        paymentService.createPaymentTypeInfo(paymentTypeInfoReq);

        // 결제 방식 정보가 자동결제인 경우 : 결제 수단을 등록한다.
        if(paymentTypeInfoReq instanceof AutoTypeReq) {

            // 결제 수단 정보 ( 카드, 실시간CMS )
            PaymentMethodInfoReq paymentMethodInfoReq = memberCreateReq.getPaymentCreateReq().getPaymentMethodInfoReq();
            paymentService.createPaymentMethodInfo(paymentMethodInfoReq);
        }

        /*
         * 회원 정보를 entity로 바꾸고 DB에 넣는다.
         * 결제 정보를 entity로 바꾸는데
         * 요청으로 들어온 결제에서 결제 방식을 확인한다.
         * 결제방식 - 가상계좌 => 결제수단 - X
         * 결제방식 - 납부자결제 => 결제수단 - X - 선택지만(리스트)
         * 결제방식 - 자동결제 => 결제수단 - O
         *                 => 결제수단 -> 실시간 CMS
         *                 => 결제수단 -> 카드
         *                 => 결제수단 -> 회원설정
         *
         * 결제방식 - 자동결제 => 결제 수단이 무엇인지 판별
         */

    }
}
