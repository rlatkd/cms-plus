package kr.or.kosa.cmsplusmain.domain.member.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.MemberContractListItemDto;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.service.ContractService;
import kr.or.kosa.cmsplusmain.domain.excel.dto.ExcelErrorRes;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberCreateReq;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDetail;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberExcelDto;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberListItemRes;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberSearchReq;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberCustomRepository;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberRepository;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final ContractService contractService;

    /*
     * 회원 목록 조회
     * */
    public PageRes<MemberListItemRes> searchMembers(Long vendorId, MemberSearchReq memberSearch, PageReq pageable) {

        int countMemberListItem = memberCustomRepository.countAllMemberByVendor(vendorId, memberSearch);

        List<MemberListItemRes> memberListItemRes = memberCustomRepository
                .findAllMemberByVendor(vendorId, memberSearch, pageable)
                .stream()
                .map(MemberListItemRes::fromEntity)
                .toList();

        return new PageRes<>(countMemberListItem, pageable.getSize(), memberListItemRes);
    }

    /*
    *  회원 상세 - 기본정보
    * */
    public MemberDetail findMemberDetailById(Long vendorId, Long memberId) {
        System.out.println("memberId : " + memberId);
        // 회원 정보 조회
        Member member = memberCustomRepository.findMemberDetailById(vendorId, memberId)
                        .orElseThrow(() -> new EntityNotFoundException("회원 ID 없음(" + memberId + ")"));

        // 청구서 수
        int billingCount = billingCustomRepository.findBillingStandardByMemberId(vendorId, memberId);

        // 총 청구 금액
        Long totalBillingPrice = billingCustomRepository.findBillingPriceByMemberId(vendorId, memberId);

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
        Member member = null;

        // 회원 존재 여부 확인 ( 휴대전화 번호 기준 )
        if(memberCustomRepository.idExistMemberByPhone(vendorId, memberCreateReq.getMemberPhone())){
            member = memberCustomRepository.findMemberByPhone(vendorId, memberCreateReq.getMemberPhone()).orElseThrow();
        }
        else {
            member = memberRepository.save(memberCreateReq.toEntity(vendorId));
        }

        // 결제 정보를 DB에 저장한다.
        Payment payment = paymentService.createPayment(memberCreateReq.getPaymentCreateReq());

        // 계약 정보를 DB에 저장한다.
        contractService.createContract(vendorId, member, payment, memberCreateReq.getContractCreateReq());
    }

    /*
    * 회원 엑셀로 대량 등록
    * */
    @Transactional()
    public List<ExcelErrorRes<MemberExcelDto>> uploadMembersByExcel(Long vendorId, List<MemberExcelDto> memberList) {
        Vendor vendor = Vendor.of(vendorId);

        List<ExcelErrorRes<MemberExcelDto>> errors = new ArrayList<>();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // 엔티티 변환
        // 변환 중 오류난 것들 따로 다시 리턴
        for (MemberExcelDto memberExcelDto : memberList) {
            Member member = memberExcelDto.toEntity(vendor);
            String errorMsg = getErrorMessage(member, validator);

            if (errorMsg == null) {
                memberRepository.save(member);
            } else {
                ExcelErrorRes<MemberExcelDto> errorRes = ExcelErrorRes.<MemberExcelDto>builder()
                    .notSaved(memberExcelDto)
                    .message(errorMsg)
                    .build();

                errors.add(errorRes);
            }
        }

        return errors;
    }

    private String getErrorMessage(Member member, Validator validator) {
        Set<ConstraintViolation<Member>> validate = validator.validate(member);
        boolean canSave = memberCustomRepository.canSaveMember(member.getPhone(), member.getEmail());

        String validation = validate.stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining("\n"));

        if (validate.isEmpty()) {
            validation += "\n";
        }

        String duplicated = canSave ? "" : "휴대번호 혹은 이메일이 중복되었습니다.";

        return (validate.isEmpty() && canSave) ? null : validation + duplicated;
    }
}
