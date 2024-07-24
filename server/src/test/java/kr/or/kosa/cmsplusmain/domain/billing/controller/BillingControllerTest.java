//package kr.or.kosa.cmsplusmain.domain.billing.controller;
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
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Random;
//
//import com.fasterxml.jackson.databind.SerializationFeature;
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
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
//
//import kr.or.kosa.cmsplusmain.RandomGenerator;
//import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
//import kr.or.kosa.cmsplusmain.domain.base.dto.PageRes;
//import kr.or.kosa.cmsplusmain.domain.base.entity.BaseEnum;
//import kr.or.kosa.cmsplusmain.domain.billing.dto.*;
//import kr.or.kosa.cmsplusmain.domain.billing.service.BillingService;
//import kr.or.kosa.cmsplusmain.domain.payment.entity.Payment;
//import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;
//import kr.or.kosa.cmsplusmain.domain.payment.entity.type.PaymentType;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//@WebMvcTest(BillingController.class)
//@AutoConfigureRestDocs
//@ExtendWith(RestDocumentationExtension.class)
//@MockBean(JpaMetamodelMappingContext.class)
//public class BillingControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private BillingService billingService;
//
//	private ObjectMapper objectMapper;
//
//	private final Random random = new Random();
//	private final RandomGenerator randomGenerator = new RandomGenerator(random);
//
//	@BeforeEach
//	public void setUp(WebApplicationContext webApplicationContext,
//		RestDocumentationContextProvider restDocumentation) {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//				.apply(documentationConfiguration(restDocumentation)
//						.operationPreprocessors()
//						.withRequestDefaults(prettyPrint())
//						.withResponseDefaults(prettyPrint())
//				)
//				.alwaysDo(MockMvcResultHandlers.print())
//				.addFilters(new CharacterEncodingFilter("UTF-8", true))
//				.build();
//
//		this.objectMapper = new ObjectMapper();
//		this.objectMapper.registerModule(new JavaTimeModule());
//		this.objectMapper.registerModule(new Jdk8Module());
//		this.objectMapper.registerModule(new ParameterNamesModule());
//		this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//		SimpleModule baseEnumModule = new SimpleModule();
//
//		baseEnumModule.addSerializer(BaseEnum.class, new JsonSerializer<BaseEnum>() {
//			@Override
//			public void serialize(BaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//				gen.writeString(value.getCode());
//			}
//		});
//
//		this.objectMapper.registerModule(baseEnumModule);
//	}
//
//	@Test
//	public void testCreateBilling() throws Exception {
//		BillingProductReq productReq = BillingProductReq.builder()
//			.productId(1L)
//			.price(random.nextInt(10000, 100000))
//			.quantity(random.nextInt(1, 5))
//			.build();
//
//		BillingCreateReq request = BillingCreateReq.builder()
//			.billingType(randomGenerator.getRandomBillingType())
//			.paymentDate(LocalDate.now().plusDays(random.nextInt(30)))
//			.contractId(1L)
//			.products(Arrays.asList(productReq))
//			.build();
//
//		mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/vendor/billing")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(request)))
//			.andExpect(status().isOk())
//			.andDo(document("billing-create",
//				requestFields(
//					fieldWithPath("billingType").description("청구 타입 (REGULAR: 정기, IRREGULAR: 추가)"),
//					fieldWithPath("paymentDate").description("결제일"),
//					fieldWithPath("contractId").description("계약 ID"),
//					fieldWithPath("products").description("청구 상품 목록"),
//					fieldWithPath("products[].productId").description("상품 ID"),
//					fieldWithPath("products[].price").description("청구 상품 가격"),
//					fieldWithPath("products[].quantity").description("청구 상품 개수")
//				)
//			));
//	}
//
//	@Test
//	public void testGetBillingListWithCondition() throws Exception {
//		BillingProductRes billingProductRes = BillingProductRes.builder()
//			.billingProductId(1L)
//			.billingId(1L)
//			.productId(1L)
//			.name("테스트 상품")
//			.price(10000)
//			.quantity(2)
//			.build();
//
//		BillingListItemRes billingListItemRes = BillingListItemRes.builder()
//			.billingId(1L)
//			.memberName("학생" + random.nextInt(100))
//			.memberPhone(randomGenerator.generateRandomPhone())
//			.billingProducts(Arrays.asList(billingProductRes))
//			.billingPrice((long) random.nextInt(10000, 100000))
//			.billingStatus(randomGenerator.getRandomBillingStatus())
//			.paymentType(randomGenerator.getRandomInList(Arrays.asList(PaymentType.values())))
//			.billingDate(LocalDate.now().plusDays(random.nextInt(30)))
//			.build();
//
//		PageRes<BillingListItemRes> pageRes = new PageRes<>(20, 9, Arrays.asList(billingListItemRes));
//
//		given(billingService.searchBillings(anyLong(), any(BillingSearchReq.class), any(PageReq.class)))
//			.willReturn(pageRes);
//
//		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/billing")
//				.param("page", "1")
//				.param("size", "9")
//				.param("order", "ASC")
//				.param("orderBy", "billingDate")
//				.param("memberName", "John")
//				.param("billingStatus", "CREATED"))
//			.andExpect(status().isOk())
//			.andDo(document("billing-list",
//				queryParameters(
//					parameterWithName("page").description("페이지 번호"),
//					parameterWithName("size").description("페이지 크기"),
//					parameterWithName("order").description("정렬 순서 (ASC/DESC)"),
//					parameterWithName("orderBy").description("정렬 기준"),
//					parameterWithName("memberName").description("회원 이름"),
//					parameterWithName("billingStatus").description("청구 상태")
//				),
//				responseFields(
//					fieldWithPath("totalCount").description("전체 요소 수"),
//					fieldWithPath("totalPage").description("전체 페이지 수"),
//					fieldWithPath("content").description("청구 목록"),
//					fieldWithPath("content[].billingId").description("청구 ID"),
//					fieldWithPath("content[].memberName").description("회원 이름"),
//					fieldWithPath("content[].memberPhone").description("회원 휴대전화"),
//					fieldWithPath("content[].billingProducts").description("청구 상품 목록"),
//					fieldWithPath("content[].billingProducts[].billingProductId").description("청구 상품 ID"),
//					fieldWithPath("content[].billingProducts[].billingId").description("청구 ID"),
//					fieldWithPath("content[].billingProducts[].productId").description("상품 ID"),
//					fieldWithPath("content[].billingProducts[].name").description("상품 이름"),
//					fieldWithPath("content[].billingProducts[].price").description("상품 가격"),
//					fieldWithPath("content[].billingProducts[].quantity").description("상품 수량"),
//					fieldWithPath("content[].billingPrice").description("청구 금액"),
//					fieldWithPath("content[].billingStatus").description("청구 상태"),
//					fieldWithPath("content[].billingStatus.code").description("청구 상태 코드"),
//					fieldWithPath("content[].billingStatus.title").description("청구 상태 제목"),
//					fieldWithPath("content[].paymentType").description("결제 방식"),
//					fieldWithPath("content[].paymentType.code").description("결제 방식 코드"),
//					fieldWithPath("content[].paymentType.title").description("결제 방식 제목"),
//					fieldWithPath("content[].billingDate").description("청구 날짜")
//				)
//			));
//	}
//
//	@Test
//	public void testGetBillingDetail() throws Exception {
//		Long billingId = 1L;
//		Payment payment = Payment.builder()
//			.paymentType(PaymentType.AUTO)
//			.paymentMethod(PaymentMethod.CARD)
//			.build();
//
//		LocalDateTime now = LocalDateTime.now();
//
//		BillingProductRes billingProductRes = BillingProductRes.builder()
//			.billingProductId(1L)
//			.billingId(billingId)
//			.productId(1L)
//			.name("테스트 상품")
//			.price(10000)
//			.quantity(2)
//			.build();
//
//		BillingDetailRes detailRes = BillingDetailRes.builder()
//			.billingId(billingId)
//			.memberId(2L)  // memberId 추가
//			.memberName("학생" + random.nextInt(100))
//			.memberPhone(randomGenerator.generateRandomPhone())
//			.contractId(1L)
//			.paymentType(payment.getPaymentType())
//			.paymentMethod(payment.getPaymentMethod())
//			.billingType(randomGenerator.getRandomBillingType())
//			.billingStatus(randomGenerator.getRandomBillingStatus())
//			.billingCreatedDate(now.minusDays(random.nextInt(30)).toLocalDate())
//			.billingDate(now.plusDays(random.nextInt(30)).toLocalDate())
//			.billingName("2023년 7월 청구서")  // billingName 추가
//			.billingMemo(randomGenerator.generateRandomInvoiceMessage())
//			.billingProducts(Arrays.asList(billingProductRes))
//			.billingPrice((long) random.nextInt(10000, 100000))
//			.invoiceSendDateTime(now.minusDays(random.nextInt(7)))
//			.paidDateTime(now.minusDays(random.nextInt(3)))
//			.canBeUpdated(true)
//			.canBeDeleted(true)
//			.canSendInvoice(true)
//			.canCancelInvoice(false)
//			.canBePaid(payment.canPayRealtime())
//			.canPayCanceled(payment.canCancel())
//			.build();
//
//		given(billingService.getBillingDetail(anyLong(), eq(billingId)))
//			.willReturn(detailRes);
//
//		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/billing/{billingId}", billingId))
//			.andExpect(status().isOk())
//			.andDo(document("billing-detail",
//				pathParameters(
//					parameterWithName("billingId").description("청구 ID")
//				),
//				responseFields(
//					fieldWithPath("billingId").description("청구 ID"),
//					fieldWithPath("memberId").description("회원 ID"),
//					fieldWithPath("memberName").description("회원 이름"),
//					fieldWithPath("memberPhone").description("회원 휴대전화"),
//					fieldWithPath("contractId").description("계약 ID"),
//					fieldWithPath("paymentType").description("결제 방식"),
//					fieldWithPath("paymentType.code").description("결제 방식 코드"),
//					fieldWithPath("paymentType.title").description("결제 방식 제목"),
//					fieldWithPath("paymentMethod").description("결제 수단"),
//					fieldWithPath("paymentMethod.code").description("결제 수단 코드"),
//					fieldWithPath("paymentMethod.title").description("결제 수단 제목"),
//					fieldWithPath("billingType").description("청구 타입"),
//					fieldWithPath("billingType.code").description("청구 타입 코드"),
//					fieldWithPath("billingType.title").description("청구 타입 제목"),
//					fieldWithPath("billingStatus").description("청구 상태"),
//					fieldWithPath("billingStatus.code").description("청구 상태 코드"),
//					fieldWithPath("billingStatus.title").description("청구 상태 제목"),
//					fieldWithPath("billingCreatedDate").description("청구 생성일"),
//					fieldWithPath("billingDate").description("청구일"),
//					fieldWithPath("billingName").description("청구서 이름"),
//					fieldWithPath("billingMemo").description("청구 메모"),
//					fieldWithPath("billingProducts").description("청구 상품 목록"),
//					fieldWithPath("billingProducts[].billingProductId").description("청구 상품 ID"),
//					fieldWithPath("billingProducts[].billingId").description("청구 ID"),
//					fieldWithPath("billingProducts[].productId").description("상품 ID"),
//					fieldWithPath("billingProducts[].name").description("상품 이름"),
//					fieldWithPath("billingProducts[].price").description("상품 가격"),
//					fieldWithPath("billingProducts[].quantity").description("상품 수량"),
//					fieldWithPath("billingPrice").description("청구 금액"),
//					fieldWithPath("invoiceSendDateTime").description("청구서 발송 시각"),
//					fieldWithPath("paidDateTime").description("결제된 시각"),
//					fieldWithPath("canBeUpdated").description("청구 수정 가능 여부"),
//					fieldWithPath("canBeDeleted").description("청구 삭제 가능 여부"),
//					fieldWithPath("canSendInvoice").description("청구서 발송 가능 여부"),
//					fieldWithPath("canCancelInvoice").description("청구서 발송 취소 가능 여부"),
//					fieldWithPath("canBePaid").description("실시간 결제 가능 여부"),
//					fieldWithPath("canPayCanceled").description("결제 취소 가능 여부")
//				)
//			));
//	}
//
//	@Test
//	public void testUpdateBilling() throws Exception {
//		Long billingId = 1L;
//		BillingProductReq productReq = BillingProductReq.builder()
//			.productId(1L)
//			.price(random.nextInt(10000, 100000))
//			.quantity(random.nextInt(1, 5))
//			.build();
//
//		BillingUpdateReq updateReq = BillingUpdateReq.builder()
//			.billingMemo(randomGenerator.generateRandomInvoiceMessage())
//			.billingDate(LocalDate.now().plusDays(random.nextInt(30)))
//			.billingProducts(Arrays.asList(productReq))
//			.build();
//
//		mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/vendor/billing/{billingId}", billingId)
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(updateReq)))
//			.andExpect(status().isOk())
//			.andDo(document("billing-update",
//				pathParameters(
//					parameterWithName("billingId").description("청구 ID")
//				),
//				requestFields(
//					fieldWithPath("billingMemo").description("청구서 메모"),
//					fieldWithPath("billingDate").description("청구일"),
//					fieldWithPath("billingProducts").description("청구 상품 목록"),
//					fieldWithPath("billingProducts[].productId").description("상품 ID"),
//					fieldWithPath("billingProducts[].price").description("청구 상품 가격"),
//					fieldWithPath("billingProducts[].quantity").description("청구 상품 개수")
//				)
//			));
//	}
//
//	@Test
//	public void testDeleteBilling() throws Exception {
//		Long billingId = 1L;
//
//		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/vendor/billing/{billingId}", billingId))
//			.andExpect(status().isOk())
//			.andDo(document("billing-delete",
//				pathParameters(
//					parameterWithName("billingId").description("청구 ID")
//				)
//			));
//	}
//
//	@Test
//	public void testSendInvoice() throws Exception {
//		Long billingId = 1L;
//
//		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/billing/invoice/{billingId}", billingId))
//			.andExpect(status().isOk())
//			.andDo(document("billing-send-invoice",
//				pathParameters(
//					parameterWithName("billingId").description("청구 ID")
//				)
//			));
//	}
//
//	@Test
//	public void testCancelInvoice() throws Exception {
//		Long billingId = 1L;
//
//		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/billing/invoice/cancel/{billingId}", billingId))
//			.andExpect(status().isOk())
//			.andDo(document("billing-cancel-invoice",
//				pathParameters(
//					parameterWithName("billingId").description("청구 ID")
//				)
//			));
//	}
//
//	@Test
//	public void testPayRealtimeBilling() throws Exception {
//		Long billingId = 1L;
//
//		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/billing/payment/{billingId}", billingId))
//			.andExpect(status().isOk())
//			.andDo(document("billing-pay-realtime",
//				pathParameters(
//					parameterWithName("billingId").description("청구 ID")
//				)
//			));
//	}
//
//	@Test
//	public void testCancelPay() throws Exception {
//		Long billingId = 1L;
//
//		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/vendor/billing/payment/{billingId}/cancel", billingId))
//			.andExpect(status().isOk())
//			.andDo(document("billing-cancel-pay",
//				pathParameters(
//					parameterWithName("billingId").description("청구 ID")
//				)
//			));
//	}
//}