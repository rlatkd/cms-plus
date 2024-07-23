//// MemberControllerTest.java
//package kr.or.kosa.cmsplusmain.domain.member.controller;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.BDDMockito.*;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.request.RequestDocumentation.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//import com.fasterxml.jackson.databind.SerializationFeature;
//import kr.or.kosa.cmsplusmain.domain.contract.dto.MemberContractListItemRes;
//import kr.or.kosa.cmsplusmain.domain.excel.dto.ExcelErrorRes;
//import kr.or.kosa.cmsplusmain.domain.kafka.MessageSendMethod;
//import kr.or.kosa.cmsplusmain.domain.payment.dto.method.PaymentMethodInfoReq;
//import kr.or.kosa.cmsplusmain.domain.payment.dto.type.PaymentTypeInfoReq;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import kr.or.kosa.cmsplusmain.RandomGenerator;
//import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
//import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
//import kr.or.kosa.cmsplusmain.domain.member.dto.MemberBillingUpdateReq;
//import kr.or.kosa.cmsplusmain.domain.member.dto.MemberCreateReq;
//import kr.or.kosa.cmsplusmain.domain.member.dto.MemberDetail;
//import kr.or.kosa.cmsplusmain.domain.member.dto.MemberExcelDto;
//import kr.or.kosa.cmsplusmain.domain.member.dto.MemberListItemRes;
//import kr.or.kosa.cmsplusmain.domain.member.dto.MemberSearchReq;
//import kr.or.kosa.cmsplusmain.domain.member.dto.MemberUpdateReq;
//import kr.or.kosa.cmsplusmain.domain.member.service.MemberService;
//import kr.or.kosa.cmsplusmain.domain.payment.dto.PaymentUpdateReq;
//import kr.or.kosa.cmsplusmain.domain.payment.service.PaymentService;
//import kr.or.kosa.cmsplusmain.domain.vendor.dto.VendorUserDetailsDto;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//@WebMvcTest(MemberController.class)
//@AutoConfigureRestDocs
//@ExtendWith(RestDocumentationExtension.class)
//@MockBean(JpaMetamodelMappingContext.class)
//public class MemberControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MemberService memberService;
//
//    @MockBean
//    private PaymentService paymentService;
//
//    private ObjectMapper objectMapper;
//
//    private final Random random = new Random();
//    private final RandomGenerator randomGenerator = new RandomGenerator(random);
//
//    @BeforeEach
//    public void setUp(WebApplicationContext webApplicationContext,
//                      RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation)
//                        .operationPreprocessors()
//                        .withRequestDefaults(prettyPrint())
//                        .withResponseDefaults(prettyPrint())
//                )
//                .alwaysDo(MockMvcResultHandlers.print())
//                .addFilters(new CharacterEncodingFilter("UTF-8", true))
//                .build();
//
//        this.objectMapper = new ObjectMapper();
//        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//    }
//
//    @Test
//    public void testGetMemberList() throws Exception {
//        MemberListItemRes memberRes = MemberListItemRes.builder()
//                .memberId(1L)
//                .memberName("학생" + random.nextInt(100))
//                .memberPhone(randomGenerator.generateRandomPhone())
//                .build();
//
//        PageRes<MemberListItemRes> pageRes = new PageRes<>(20, 9, Arrays.asList(memberRes));
//
//        given(memberService.searchMembers(anyLong(), any(MemberSearchReq.class), any(PageReq.class)))
//                .willReturn(pageRes);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/management/members")
//                        .param("page", "1")
//                        .param("size", "9")
//                        .param("order", "ASC")
//                        .param("orderBy", "memberName")
//                        .param("memberName", "John"))
//                .andExpect(status().isOk())
//                .andDo(document("member-list",
//                        queryParameters(
//                                parameterWithName("page").description("페이지 번호"),
//                                parameterWithName("size").description("페이지 크기"),
//                                parameterWithName("order").description("정렬 순서 (ASC/DESC)"),
//                                parameterWithName("orderBy").description("정렬 기준"),
//                                parameterWithName("memberName").description("회원 이름")
//                        ),
//                        responseFields(
//                                fieldWithPath("totalCount").description("전체 요소 수"),
//                                fieldWithPath("totalPage").description("전체 페이지 수"),
//                                fieldWithPath("content").description("회원 목록"),
//                                fieldWithPath("content[].memberId").description("회원 ID"),
//                                fieldWithPath("content[].memberName").description("회원 이름"),
//                                fieldWithPath("content[].memberPhone").description("회원 휴대전화")
//                        )
//                ));
//    }
//
//    @Test
//    public void testGetMemberDetail() throws Exception {
//        Long memberId = 1L;
//
//        MemberDetail memberDetail = MemberDetail.builder()
//                .memberId(memberId)
//                .memberName("학생" + random.nextInt(100))
//                .memberPhone(randomGenerator.generateRandomPhone())
//                .build();
//
//        given(memberService.findMemberDetailById(anyLong(), eq(memberId)))
//                .willReturn(memberDetail);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/management/members/{memberId}", memberId))
//                .andExpect(status().isOk())
//                .andDo(document("member-detail",
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 ID")
//                        ),
//                        responseFields(
//                                fieldWithPath("memberId").description("회원 ID"),
//                                fieldWithPath("memberName").description("회원 이름"),
//                                fieldWithPath("memberPhone").description("회원 휴대전화"),
//                                fieldWithPath("memberHomePhone").description("회원 유선전화").optional(),
//                                fieldWithPath("memberEmail").description("회원 이메일"),
//                                fieldWithPath("memberAddress.zipcode").description("우편번호"),
//                                fieldWithPath("memberAddress.address").description("주소"),
//                                fieldWithPath("memberAddress.addressDetail").description("상세주소"),
//                                fieldWithPath("memberMemo").description("회원 메모").optional(),
//                                fieldWithPath("memberEnrollDate").description("회원 등록일"),
//                                fieldWithPath("invoiceSendMethod").description("청구 전송 방법"),
//                                fieldWithPath("autoInvoiceSend").description("자동 청구서 발송 여부"),
//                                fieldWithPath("autoBilling").description("자동 청구 발송 여부")
//                        )
//                ));
//    }
//
//    @Test
//    public void testCreateMember() throws Exception {
//        MemberCreateReq request = new MemberCreateReq(
//                "학생" + random.nextInt(100),
//                randomGenerator.generateRandomPhone(),
//                LocalDate.now(),
//                randomGenerator.generateRandomPhone(),
//                "test@example.com",
//                new Address("12345", "서울시 강남구", "아파트 101동 202호"),
//                "테스트 메모",
//                MessageSendMethod.EMAIL,
//                true,
//                true,
//                null,
//                null
//        );
//
//        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/vendor/management/members")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andDo(document("member-create",
//                        requestFields(
//                                fieldWithPath("memberName").description("회원 이름"),
//                                fieldWithPath("memberPhone").description("회원 휴대전화"),
//                                fieldWithPath("memberEnrollDate").description("회원 등록 날짜"),
//                                fieldWithPath("memberHomePhone").description("회원 유선전화 번호").optional(),
//                                fieldWithPath("memberEmail").description("회원 이메일"),
//                                fieldWithPath("memberAddress.zipcode").description("회원 주소 우편번호").optional(),
//                                fieldWithPath("memberAddress.address").description("회원 주소").optional(),
//                                fieldWithPath("memberAddress.addressDetail").description("회원 주소 상세").optional(),
//                                fieldWithPath("memberMemo").description("회원 메모").optional(),
//                                fieldWithPath("invoiceSendMethod").description("청구 전송 방법"),
//                                fieldWithPath("autoInvoiceSend").description("자동 청구서 발송"),
//                                fieldWithPath("autoBilling").description("자동 청구 발송")
//                        )
//                ));
//    }
//
//    @Test
//    public void testUpdateMember() throws Exception {
//        Long memberId = 1L;
//        MemberUpdateReq updateReq = new MemberUpdateReq(
//                "학생" + random.nextInt(100),
//                randomGenerator.generateRandomPhone(),
//                LocalDate.now(),
//                randomGenerator.generateRandomPhone(),
//                "test@example.com",
//                new Address("12345", "서울시 강남구", "아파트 101동 202호"),
//                "테스트 메모"
//        );
//
//        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/vendor/management/members/{memberId}", memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateReq)))
//                .andExpect(status().isOk())
//                .andDo(document("member-update",
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 ID")
//                        ),
//                        requestFields(
//                                fieldWithPath("memberName").description("회원 이름"),
//                                fieldWithPath("memberPhone").description("회원 휴대전화"),
//                                fieldWithPath("memberEnrollDate").description("회원 등록 날짜"),
//                                fieldWithPath("memberHomePhone").description("회원 유선전화 번호").optional(),
//                                fieldWithPath("memberEmail").description("회원 이메일"),
//                                fieldWithPath("memberAddress.zipcode").description("회원 주소 우편번호").optional(),
//                                fieldWithPath("memberAddress.address").description("회원 주소").optional(),
//                                fieldWithPath("memberAddress.addressDetail").description("회원 주소 상세").optional(),
//                                fieldWithPath("memberMemo").description("회원 메모").optional()
//                        )
//                ));
//    }
//
//    @Test
//    public void testDeleteMember() throws Exception {
//        Long memberId = 1L;
//
//        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/vendor/management/members/{memberId}", memberId))
//                .andExpect(status().isOk())
//                .andDo(document("member-delete",
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 ID")
//                        )
//                ));
//    }
//
//    @Test
//    public void testUploadMembersByExcel() throws Exception {
//        MemberExcelDto excelDto = MemberExcelDto.builder()
//                .memberName("학생" + random.nextInt(100))
//                .memberPhone(randomGenerator.generateRandomPhone())
//                .zipcode("12345")
//                .address("서울시 강남구")
//                .addressDetail("아파트 101동 202호")
//                .memberEnrollDate(LocalDate.now())
//                .memberHomePhone(randomGenerator.generateRandomPhone())
//                .memberEmail("test@example.com")
//                .build();
//
//        List<MemberExcelDto> excelDtoList = Arrays.asList(excelDto);
//
//        given(memberService.uploadMembersByExcel(anyLong(), anyList()))
//                .willReturn(Arrays.asList(new ExcelErrorRes<>(excelDto, "오류 메시지")));
//
//        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/vendor/management/upload")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(excelDtoList)))
//                .andExpect(status().isOk())
//                .andDo(document("member-upload",
//                        requestFields(
//                                fieldWithPath("[].memberName").description("회원 이름"),
//                                fieldWithPath("[].memberPhone").description("회원 휴대전화"),
//                                fieldWithPath("[].zipcode").description("우편번호"),
//                                fieldWithPath("[].address").description("주소"),
//                                fieldWithPath("[].addressDetail").description("상세주소"),
//                                fieldWithPath("[].memberEnrollDate").description("등록일"),
//                                fieldWithPath("[].memberHomePhone").description("유선전화").optional(),
//                                fieldWithPath("[].memberEmail").description("이메일")
//                        ),
//                        responseFields(
//                                fieldWithPath("[].entity.memberName").description("회원 이름"),
//                                fieldWithPath("[].entity.memberPhone").description("회원 휴대전화"),
//                                fieldWithPath("[].entity.zipcode").description("우편번호"),
//                                fieldWithPath("[].entity.address").description("주소"),
//                                fieldWithPath("[].entity.addressDetail").description("상세주소"),
//                                fieldWithPath("[].entity.memberEnrollDate").description("등록일"),
//                                fieldWithPath("[].entity.memberHomePhone").description("유선전화").optional(),
//                                fieldWithPath("[].entity.memberEmail").description("이메일"),
//                                fieldWithPath("[].errorMessage").description("오류 메시지")
//                        )
//                ));
//    }
//
//    @Test
//    public void testConvertMembersByExcel() throws Exception {
//        MemberExcelDto excelDto = MemberExcelDto.builder()
//                .memberName("학생" + random.nextInt(100))
//                .memberPhone(randomGenerator.generateRandomPhone())
//                .zipcode("12345")
//                .address("서울시 강남구")
//                .addressDetail("아파트 101동 202호")
//                .memberEnrollDate(LocalDate.now())
//                .memberHomePhone(randomGenerator.generateRandomPhone())
//                .memberEmail("test@example.com")
//                .build();
//
//        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/vendor/management/convert")
//                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//                        .param("file", "file"))
//                .andExpect(status().isOk())
//                .andDo(document("member-convert",
//                        requestParameters(
//                                parameterWithName("file").description("엑셀 파일")
//                        ),
//                        responseFields(
//                                fieldWithPath("[].memberName").description("회원 이름"),
//                                fieldWithPath("[].memberPhone").description("회원 휴대전화"),
//                                fieldWithPath("[].zipcode").description("우편번호"),
//                                fieldWithPath("[].address").description("주소"),
//                                fieldWithPath("[].addressDetail").description("상세주소"),
//                                fieldWithPath("[].memberEnrollDate").description("등록일"),
//                                fieldWithPath("[].memberHomePhone").description("유선전화").optional(),
//                                fieldWithPath("[].memberEmail").description("이메일")
//                        )
//                ));
//    }
//
//    @Test
//    public void testGetMemberContractList() throws Exception {
//        Long memberId = 1L;
//        MemberContractListItemRes contractRes = MemberContractListItemRes.builder()
//                .contractId(1L)
//                .contractName("계약" + random.nextInt(100))
//                .build();
//
//        PageRes<MemberContractListItemRes> pageRes = new PageRes<>(20, 9, Arrays.asList(contractRes));
//
//        given(memberService.findContractListItemByMemberId(anyLong(), eq(memberId), any(PageReq.class)))
//                .willReturn(pageRes);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/management/members/contracts/{memberId}", memberId)
//                        .param("page", "1")
//                        .param("size", "9")
//                        .param("order", "ASC")
//                        .param("orderBy", "contractName"))
//                .andExpect(status().isOk())
//                .andDo(document("member-contract-list",
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 ID")
//                        ),
//                        queryParameters(
//                                parameterWithName("page").description("페이지 번호"),
//                                parameterWithName("size").description("페이지 크기"),
//                                parameterWithName("order").description("정렬 순서 (ASC/DESC)"),
//                                parameterWithName("orderBy").description("정렬 기준")
//                        ),
//                        responseFields(
//                                fieldWithPath("totalCount").description("전체 요소 수"),
//                                fieldWithPath("totalPage").description("전체 페이지 수"),
//                                fieldWithPath("content").description("계약 목록"),
//                                fieldWithPath("content[].contractId").description("계약 ID"),
//                                fieldWithPath("content[].contractName").description("계약 이름")
//                        )
//                ));
//    }
//
//    @Test
//    public void testUpdateMemberBilling() throws Exception {
//        Long memberId = 1L;
//        MemberBillingUpdateReq updateReq = new MemberBillingUpdateReq(
//                MessageSendMethod.EMAIL,
//                true,
//                true
//        );
//
//        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/vendor/management/members/billing/{memberId}", memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateReq)))
//                .andExpect(status().isOk())
//                .andDo(document("member-billing-update",
//                        pathParameters(
//                                parameterWithName("memberId").description("회원 ID")
//                        ),
//                        requestFields(
//                                fieldWithPath("invoiceSendMethod").description("청구 전송 방법"),
//                                fieldWithPath("autoInvoiceSend").description("자동 청구서 발송"),
//                                fieldWithPath("autoBilling").description("자동 청구 발송")
//                        )
//                ));
//    }
//
//    @Test
//    public void testUpdateMemberPayment() throws Exception {
//        Long contractId = 1L;
//        PaymentUpdateReq updateReq = new PaymentUpdateReq(
//                new PaymentTypeInfoReq(),
//                new PaymentMethodInfoReq()
//        );
//
//        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/vendor/management/members/payment/{contractId}", contractId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateReq)))
//                .andExpect(status().isOk())
//                .andDo(document("member-payment-update",
//                        pathParameters(
//                                parameterWithName("contractId").description("계약 ID")
//                        ),
//                        requestFields(
//                                fieldWithPath("paymentTypeInfoReq").description("결제 유형 정보"),
//                                fieldWithPath("paymentMethodInfoReq").description("결제 방법 정보").optional()
//                        )
//                ));
//    }
//}
