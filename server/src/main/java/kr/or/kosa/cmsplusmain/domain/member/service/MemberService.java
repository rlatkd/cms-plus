package kr.or.kosa.cmsplusmain.domain.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import kr.or.kosa.cmsplusmain.domain.member.repository.V2MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
import kr.or.kosa.cmsplusmain.domain.billing.repository.BillingCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.dto.response.MemberContractListItemRes;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractCustomRepository;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractProductRepository;
import kr.or.kosa.cmsplusmain.domain.contract.repository.ContractRepository;
import kr.or.kosa.cmsplusmain.domain.contract.service.ContractService;
import kr.or.kosa.cmsplusmain.domain.excel.dto.ExcelErrorRes;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberBillingUpdateReq;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberCreateReq;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDetail;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDto;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberExcelDto;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberListItemRes;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberSearchReq;
import kr.or.kosa.cmsplusmain.domain.member.dto.MemberUpdateReq;
import kr.or.kosa.cmsplusmain.domain.member.entity.Member;
import kr.or.kosa.cmsplusmain.domain.member.exception.MemberNotFoundException;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberCustomRepository;
import kr.or.kosa.cmsplusmain.domain.member.repository.MemberRepository;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final ContractRepository contractRepository;
    private final ContractProductRepository contractProductRepository;

    private final V2MemberRepository v2MemberRepository;

    /**
     * 회원 목록 조회
     * */
    public PageRes<MemberListItemRes> searchMembers(Long vendorId, MemberSearchReq memberSearch, PageReq pageable) {

        int countMemberListItem = memberCustomRepository.countAllMemberByVendor(vendorId, memberSearch);

        List<MemberListItemRes> memberListItemRes = memberCustomRepository
                .searchAllMemberByVendor(vendorId, memberSearch, pageable)
                .stream()
                .map(MemberListItemRes::fromEntity)
                .toList();

//        List<MemberListItemRes> memberListItemRes = v2MemberRepository
//                .searchAllMemberByVendor(vendorId, memberSearch, pageable);

        return new PageRes<>(countMemberListItem, pageable.getSize(), memberListItemRes);
    }

    /**
     * 해당 고객 회원의 기본정보 목록 조회
     * */
    public PageRes<MemberDto> findAllMemberBasicInfo(Long vendorId, MemberSearchReq memberSearch, PageReq pageable) {
        int totalCount = memberCustomRepository.countAllMembers(vendorId, memberSearch);
        List<MemberDto> members = memberCustomRepository.findAllMembers(vendorId, memberSearch, pageable).stream()
            .map(MemberDto::fromEntity)
            .toList();
        return new PageRes<>(totalCount, pageable.getSize(), members);
    }

    /**
    *  회원 상세 - 기본정보
    * */
    public MemberDetail findMemberDetailById(Long vendorId, Long memberId) {
        System.out.println("memberId : " + memberId);
        // 회원 정보 조회
        Member member = memberCustomRepository.findMemberDetailById(vendorId, memberId)
                        .orElseThrow(() -> new MemberNotFoundException("회원 ID 없음(" + memberId + ")"));

        // 청구서 수
        int billingCount = billingCustomRepository.findBillingStandardByMemberId(vendorId, memberId);

        // 총 청구 금액
        Long totalBillingPrice = billingCustomRepository.findBillingPriceByMemberId(vendorId, memberId);

        return MemberDetail.fromEntity(member, billingCount, totalBillingPrice);
    }

    /**
    * 회원 상세 - 계약리스트
    * */
    public PageRes<MemberContractListItemRes> findContractListItemByMemberId(Long vendorId, Long memberId, PageReq pageable) {

        int countAllContractListItemByMemberId = contractCustomRepository.countContractListItemByMemberId(vendorId, memberId);

        List<MemberContractListItemRes> memberContractListItemRes = contractCustomRepository
                .findContractListItemByMemberId(vendorId, memberId, pageable)
                .stream()
                .map(MemberContractListItemRes::fromEntity)
                .toList();

        return new PageRes<>(countAllContractListItemByMemberId,pageable.getSize(), memberContractListItemRes);
    }

    /**
     * 회원 등록
     * */
    @Transactional
    public Long createMember(Long vendorId, MemberCreateReq memberCreateReq) {

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
        Long contractId = contractService.createContract(vendorId, member, payment, memberCreateReq.getContractCreateReq());

        return contractId;
    }

    /**
     * 회원 수정 - 기본 정보
     *
     * 총 발생 쿼리수: 3회
     * 내용 :
     *    회원 존재여부 확인, 회원 상세 조회, 회원 정보 업데이트
     * */
    @Transactional
    public void updateMember(Long vendorId, Long memberId, MemberUpdateReq memberUpdateReq) {
        // 고객의 회원 여부 확인
         validateMemberUser(vendorId, memberId);

        // 회원의 기본 정보 수정
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        member.setName(memberUpdateReq.getMemberName());
        member.setPhone(memberUpdateReq.getMemberPhone());
        member.setEnrollDate(memberUpdateReq.getMemberEnrollDate());
        member.setHomePhone(memberUpdateReq.getMemberHomePhone());
        member.setEmail(memberUpdateReq.getMemberEmail());
        member.setAddress(memberUpdateReq.getMemberAddress());
        member.setMemo(memberUpdateReq.getMemberMemo());
    }

    /**
     * 회원 수정 - 청구 정보
     * */
    @Transactional
    public void updateMemberBilling(Long vendorId, Long memberId, MemberBillingUpdateReq memberBillingUpdateReq){

        // 고객의 회원 여부 확인
        validateMemberUser(vendorId, memberId);

        // 회원의 청구 정보 수정
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        member.setInvoiceSendMethod(memberBillingUpdateReq.getInvoiceSendMethod());
        member.setAutoInvoiceSend(memberBillingUpdateReq.isAutoInvoiceSend());
        member.setAutoBilling(memberBillingUpdateReq.isAutoBilling());
    }

    /**
     * 회원 삭제
     * */
    @Transactional
    public void deleteMember(Long vendorId, Long memberId) {

        // 고객의 회원 여부 확인
        validateMemberUser(vendorId, memberId);

        // 고객정보 삭제
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        member.delete();
    }

    /**
     * 회원 삭제 - 회원의 진행중인 청구 개수 조회
     * */
    public int countAllInProgressBillingByMember(Long vendorId, Long memberId) {
        validateMemberUser(vendorId, memberId);
        return billingCustomRepository.countInProgressBillingsByMember(memberId);
    }

    /**
    * 회원 엑셀로 대량 등록
    * */
    @Transactional
    public List<ExcelErrorRes<MemberExcelDto>> uploadMembersByExcel(Long vendorId, List<MemberExcelDto> memberList) {
        Vendor vendor = Vendor.of(vendorId);

        List<Member> toSaves = new ArrayList<>(memberList.size());
        List<ExcelErrorRes<MemberExcelDto>> errors = new ArrayList<>();

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            // 변환 중 오류난 것들 따로 다시 리턴
            for (MemberExcelDto memberExcelDto : memberList) {
                if (memberExcelDto == null) {
                    ExcelErrorRes<MemberExcelDto> errorRes = ExcelErrorRes.<MemberExcelDto>builder()
                        .notSaved(memberExcelDto)
                        .message("형식이 잘못된 값 입력")
                        .build();

                    errors.add(errorRes);
                    continue;
                }

                Member member = memberExcelDto.toEntity(vendor);
                String errorMsg = getErrorMessage(member, validator);

                if (errorMsg == null) {
                    toSaves.add(member);
                } else {
                    ExcelErrorRes<MemberExcelDto> errorRes = ExcelErrorRes.<MemberExcelDto>builder()
                        .notSaved(memberExcelDto)
                        .message(errorMsg)
                        .build();

                    errors.add(errorRes);
                }
            }
        } catch (Exception e) {
            log.error("upload members by excel {}", e.getMessage());
            throw new IllegalArgumentException("upload members by excel " + e.getMessage());
        } finally {
            memberCustomRepository.bulkInsert(toSaves);
        }

        return errors;
    }

    private String getErrorMessage(Member member, Validator validator) {
        Set<ConstraintViolation<Member>> validate = validator.validate(member);
        boolean canSave = memberCustomRepository.canSaveMember(member.getPhone(), member.getEmail());

        String validation = validate.stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining("\n"));

        if (!validate.isEmpty()) {
            validation += "\n";
        }

        String duplicated = canSave ? "" : "휴대전화 혹은 이메일이 중복되었습니다";

        return (validate.isEmpty() && canSave) ? null : validation + duplicated;
    }

    /**
     * 회원 ID 존재여부
     * 회원이 현재 로그인 고객의 회원인지 여부
     * */
    private void validateMemberUser(Long vendorId, Long memberId) {
        if(!memberCustomRepository.isExistMemberById(memberId, vendorId)) {
            throw new MemberNotFoundException("회원이 존재하지 않습니다");
        }
    }

}
