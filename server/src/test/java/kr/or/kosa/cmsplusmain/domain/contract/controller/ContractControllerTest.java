//package kr.or.kosa.cmsplusmain.domain.contract.controller;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.BDDMockito.*;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.request.RequestDocumentation.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEnum;
//import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingListItemRes;
//import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
//import kr.or.kosa.cmsplusmain.domain.contract.entity.ContractStatus;
//import kr.or.kosa.cmsplusmain.domain.payment.dto.method.CardMethodRes;
//import kr.or.kosa.cmsplusmain.domain.payment.dto.method.PaymentMethodInfoRes;
//import kr.or.kosa.cmsplusmain.domain.payment.dto.type.AutoTypeRes;
//import kr.or.kosa.cmsplusmain.domain.payment.dto.type.PaymentTypeInfoRes;
//import kr.or.kosa.cmsplusmain.domain.payment.entity.ConsentStatus;
//import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
//import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
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
//import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
//
//import kr.or.kosa.cmsplusmain.RandomGenerator;
//import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
//import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
//import kr.or.kosa.cmsplusmain.domain.contract.dto.*;
//import kr.or.kosa.cmsplusmain.domain.contract.service.ContractService;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//@WebMvcTest(ContractController.class)
//@AutoConfigureRestDocs
//@ExtendWith(RestDocumentationExtension.class)
//@MockBean(JpaMetamodelMappingContext.class)
//public class ContractControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ContractService contractService;
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
//        this.objectMapper = new ObjectMapper();
//        this.objectMapper.registerModule(new JavaTimeModule());
//        this.objectMapper.registerModule(new Jdk8Module());
//        this.objectMapper.registerModule(new ParameterNamesModule());
//        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//        SimpleModule baseEnumModule = new SimpleModule();
//
//        baseEnumModule.addSerializer(BaseEnum.class, new JsonSerializer<BaseEnum>() {
//            @Override
//            public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//                gen.writeString(value.getCode());
//            }
//        });
//
//        this.objectMapper.registerModule(baseEnumModule);
//    }
//
//    @Test
//    public void testGetContractListWithCondition() throws Exception {
//        ContractProductRes contractProductRes = ContractProductRes.builder()
//                .contractProductId(1L)
//                .contractId(1L)
//                .productId(1L)
//                .name("테스트 상품")
//                .price(10000)
//                .quantity(2)
//                .build();
//
//        ContractListItemRes contractListItemRes = ContractListItemRes.builder()
//                .contractId(1L)
//                .memberName("학생")
//                .memberPhone("010-1234-5678")
//                .contractDay(15)
//                .contractProducts(Collections.singletonList(contractProductRes))
//                .contractPrice(100000L)
//                .paymentType(PaymentType.AUTO)
//                .contractStatus(ContractStatus.ENABLED)
//                .build();
//
//        PageRes<ContractListItemRes> pageRes = new PageRes<>(20, 9, Collections.singletonList(contractListItemRes));
//
//        given(contractService.searchContracts(anyLong(), any(ContractSearchReq.class), any(PageReq.class)))
//                .willReturn(pageRes);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/contract")
//                        .param("page", "1")
//                        .param("size", "9")
//                        .param("order", "ASC")
//                        .param("orderBy", "contractStartDate")
//                        .param("memberName", "John")
//                        .param("contractStatus", "ENABLED"))
//                .andExpect(status().isOk())
//                .andDo(document("contract-list",
//                        queryParameters(
//                                parameterWithName("page").description("페이지 번호"),
//                                parameterWithName("size").description("페이지 크기"),
//                                parameterWithName("order").description("정렬 순서 (ASC/DESC)"),
//                                parameterWithName("orderBy").description("정렬 기준"),
//                                parameterWithName("memberName").description("회원 이름").optional(),
//                                parameterWithName("contractStatus").description("계약 상태").optional()
//                        ),
//                        responseFields(
//                                fieldWithPath("totalCount").description("전체 요소 수"),
//                                fieldWithPath("totalPage").description("전체 페이지 수"),
//                                fieldWithPath("content").description("계약 목록"),
//                                fieldWithPath("content[].contractId").description("계약 ID"),
//                                fieldWithPath("content[].memberName").description("회원 이름"),
//                                fieldWithPath("content[].memberPhone").description("회원 휴대번호"),
//                                fieldWithPath("content[].contractDay").description("약정일"),
//                                fieldWithPath("content[].contractProducts").description("계약 상품 목록"),
//                                fieldWithPath("content[].contractProducts[].contractProductId").description("계약상품 ID"),
//                                fieldWithPath("content[].contractProducts[].contractId").description("계약 ID"),
//                                fieldWithPath("content[].contractProducts[].productId").description("상품 ID"),
//                                fieldWithPath("content[].contractProducts[].name").description("상품명"),
//                                fieldWithPath("content[].contractProducts[].price").description("계약상품 가격"),
//                                fieldWithPath("content[].contractProducts[].quantity").description("계약상품 수량"),
//                                fieldWithPath("content[].contractPrice").description("계약금액"),
//                                fieldWithPath("content[].paymentType.code").description("결제 방식 코드"),
//                                fieldWithPath("content[].paymentType.title").description("결제 방식 제목"),
//                                fieldWithPath("content[].contractStatus.code").description("계약 상태 코드"),
//                                fieldWithPath("content[].contractStatus.title").description("계약 상태 제목")
//                        )
//                ));
//    }
//
//    @Test
//    public void testGetContractDetail() throws Exception {
//        Long contractId = 1L;
//
//        ContractProductRes contractProductRes = ContractProductRes.builder()
//                .contractProductId(1L)
//                .contractId(contractId)
//                .productId(1L)
//                .name("테스트 상품")
//                .price(10000)
//                .quantity(2)
//                .build();
//
//        AutoTypeRes autoTypeRes = AutoTypeRes.builder()
//                .signImgUrl("http://example.com/sign")
//                .consentImgUrl("http://example.com/consent")
//                .simpleConsentReqDateTime(LocalDateTime.now().minusDays(10))
//                .consentStatus(ConsentStatus.ACCEPT)
//                .build();
//
//        CardMethodRes cardMethodRes = CardMethodRes.builder()
//                .cardNumber("1234-5678-9012-3456")
//                .cardMonth(12)
//                .cardYear(2025)
//                .cardOwner("John Doe")
//                .cardOwnerBirth(LocalDate.of(1990, 1, 1))
//                .build();
//
//        ContractDetailRes contractDetailRes = ContractDetailRes.builder()
//                .contractId(contractId)
//                .contractName("테스트 계약")
//                .createdDateTime(LocalDateTime.now().minusDays(5))
//                .modifiedDateTime(LocalDateTime.now().minusDays(1))
//                .contractProducts(Collections.singletonList(contractProductRes))
//                .contractPrice(100000L)
//                .contractDay(15)
//                .contractStartDate(LocalDate.now().minusMonths(1))
//                .contractEndDate(LocalDate.now().plusMonths(1))
//                .paymentTypeInfo(autoTypeRes)
//                .paymentMethodInfo(cardMethodRes)
//                .totalBillingCount(5L)
//                .totalBillingPrice(500000L)
//                .build();
//
//        given(contractService.getContractDetail(anyLong(), eq(contractId)))
//                .willReturn(contractDetailRes);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/contract/{contractId}", contractId))
//                .andExpect(status().isOk())
//                .andDo(document("contract-detail",
//                        pathParameters(
//                                parameterWithName("contractId").description("계약 ID")
//                        ),
//                        responseFields(
//                                fieldWithPath("contractId").description("계약 ID"),
//                                fieldWithPath("contractName").description("계약 이름"),
//                                fieldWithPath("createdDateTime").description("계약 등록일시"),
//                                fieldWithPath("modifiedDateTime").description("계약 변경일시"),
//                                fieldWithPath("contractProducts").description("계약 상품 목록"),
//                                fieldWithPath("contractProducts[].contractProductId").description("계약상품 ID"),
//                                fieldWithPath("contractProducts[].contractId").description("계약 ID"),
//                                fieldWithPath("contractProducts[].productId").description("상품 ID"),
//                                fieldWithPath("contractProducts[].name").description("상품명"),
//                                fieldWithPath("contractProducts[].price").description("계약상품 가격"),
//                                fieldWithPath("contractProducts[].quantity").description("계약상품 수량"),
//                                fieldWithPath("contractPrice").description("계약금액"),
//                                fieldWithPath("contractDay").description("약정일"),
//                                fieldWithPath("contractStartDate").description("계약 시작일"),
//                                fieldWithPath("contractEndDate").description("계약 종료일"),
//                                fieldWithPath("paymentTypeInfo.paymentType.code").description("결제방식 코드"),
//                                fieldWithPath("paymentTypeInfo.paymentType.title").description("결제방식 제목"),
//                                fieldWithPath("paymentTypeInfo.signImgUrl").description("서명 이미지 URL"),
//                                fieldWithPath("paymentTypeInfo.consentImgUrl").description("동의 이미지 URL"),
//                                fieldWithPath("paymentTypeInfo.simpleConsentReqDateTime").description("간편 동의 요청 시간"),
//                                fieldWithPath("paymentTypeInfo.consentStatus.code").description("동의 상태 코드"),
//                                fieldWithPath("paymentTypeInfo.consentStatus.title").description("동의 상태 제목"),
//                                fieldWithPath("paymentMethodInfo.paymentMethod.code").description("결제수단 코드"),
//                                fieldWithPath("paymentMethodInfo.paymentMethod.title").description("결제수단 제목"),
//                                fieldWithPath("paymentMethodInfo.cardNumber").description("카드 번호"),
//                                fieldWithPath("paymentMethodInfo.cardMonth").description("카드 만료 월"),
//                                fieldWithPath("paymentMethodInfo.cardYear").description("카드 만료 연도"),
//                                fieldWithPath("paymentMethodInfo.cardOwner").description("카드 소유자"),
//                                fieldWithPath("paymentMethodInfo.cardOwnerBirth").description("카드 소유자 생년월일"),
//                                fieldWithPath("totalBillingCount").description("청구 총 개수"),
//                                fieldWithPath("totalBillingPrice").description("청구 총 금액")
//                        )
//                ));
//    }
//
//
//    @Test
//    public void testGetBillingListByContract() throws Exception {
//        Long contractId = 1L;
//
//        BillingListItemRes billingListItemRes = BillingListItemRes.builder()
//                .billingId(1L)
//                .billingPrice(10000L)
//                .billingStatus(BillingStatus.PAID)
//                .billingDate(LocalDate.now())
//                .memberName("학생")
//                .memberPhone("010-1234-5678")
//                .billingProducts(Collections.emptyList())
//                .paymentType(PaymentType.AUTO)
//                .build();
//
//        PageRes<BillingListItemRes> pageRes = new PageRes<>(20, 9, Arrays.asList(billingListItemRes));
//
//        given(contractService.getBillingsByContract(anyLong(), eq(contractId), any(PageReq.class)))
//                .willReturn(pageRes);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/contract/{contractId}/billing", contractId)
//                        .param("page", "1")
//                        .param("size", "9"))
//                .andExpect(status().isOk())
//                .andDo(document("contract-billing-list",
//                        pathParameters(
//                                parameterWithName("contractId").description("계약 ID")
//                        ),
//                        queryParameters(
//                                parameterWithName("page").description("페이지 번호"),
//                                parameterWithName("size").description("페이지 크기")
//                        ),
//                        responseFields(
//                                fieldWithPath("totalCount").description("전체 요소 수"),
//                                fieldWithPath("totalPage").description("전체 페이지 수"),
//                                fieldWithPath("content").description("청구 목록"),
//                                fieldWithPath("content[].billingId").description("청구 ID"),
//                                fieldWithPath("content[].billingPrice").description("청구 금액"),
//                                fieldWithPath("content[].billingStatus.code").description("청구 상태 코드"),
//                                fieldWithPath("content[].billingStatus.title").description("청구 상태 제목"),
//                                fieldWithPath("content[].billingDate").description("청구 날짜"),
//                                fieldWithPath("content[].memberName").description("회원 이름").optional(),
//                                fieldWithPath("content[].memberPhone").description("회원 전화번호").optional(),
//                                fieldWithPath("content[].billingProducts").description("청구 상품 목록").optional(),
//                                fieldWithPath("content[].paymentType.code").description("결제 방식 코드").optional(),
//                                fieldWithPath("content[].paymentType.title").description("결제 방식 제목").optional()
//                        )
//                ));
//    }
//
//    @Test
//    public void testUpdateContract() throws Exception {
//        Long contractId = 1L;
//
//        List<ContractProductReq> contractProducts = Arrays.asList(
//                ContractProductReq.builder()
//                        .productId(1L)
//                        .price(20000)
//                        .quantity(1)
//                        .build()
//        );
//
//        ContractUpdateReq contractUpdateReq = new ContractUpdateReq();
//        setField(contractUpdateReq, "contractName", "업데이트된계약1");
//        setField(contractUpdateReq, "contractProducts", contractProducts);
//
//        String contractUpdateReqJson = objectMapper.writeValueAsString(contractUpdateReq);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/vendor/contract/{contractId}", contractId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(contractUpdateReqJson))
//                .andExpect(status().isOk())
//                .andDo(document("contract-update",
//                        pathParameters(
//                                parameterWithName("contractId").description("계약 ID")
//                        ),
//                        requestFields(
//                                fieldWithPath("contractName").description("계약 이름"),
//                                fieldWithPath("contractProducts").description("계약 상품 목록"),
//                                fieldWithPath("contractProducts[].productId").description("상품 ID"),
//                                fieldWithPath("contractProducts[].price").description("계약 상품 가격"),
//                                fieldWithPath("contractProducts[].quantity").description("계약 상품 수량")
//                        )
//                ));
//    }
//
//    private void setField(Object targetObject, String fieldName, Object value) throws Exception {
//        Field field = targetObject.getClass().getDeclaredField(fieldName);
//        field.setAccessible(true);
//        field.set(targetObject, value);
//    }
//
//}
