package kr.or.kosa.cmsplusmain.domain.products.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@ExtendWith({RestDocumentationExtension.class})
@AutoConfigureMockMvc
@SpringBootTest
class ProductsTestController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .operationPreprocessors()
                        .withRequestDefaults(modifyUris().host("localhost").removePort(), prettyPrint())
                        .withResponseDefaults(modifyUris().host("localhost").removePort(), prettyPrint()))
                .build();
    }

    @Test
    void getProducts() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vendor/products")
                        .param("page", "1")
                        .param("size", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("getProducts",
                        HeaderDocumentation.requestHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.ACCEPT).description("accept header")
                        ),
                        HeaderDocumentation.responseHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("page").description("current page number"),
                                PayloadDocumentation.fieldWithPath("size").description("number of items per page"),
                                PayloadDocumentation.fieldWithPath("totalPage").description("total number of pages"),
                                PayloadDocumentation.fieldWithPath("totalCount").description("total number of products"),
                                PayloadDocumentation.fieldWithPath("data[].name").description("name of product"),
                                PayloadDocumentation.fieldWithPath("data[].price").description("price of product"),
                                PayloadDocumentation.fieldWithPath("data[].contractCount").description("contract count of product"),
                                PayloadDocumentation.fieldWithPath("data[].createdAt").description("creation date of product"),
                                PayloadDocumentation.fieldWithPath("data[].notes").description("notes of product")
                        )
                ));
    }

}