package kr.or.kosa.cmsplusmain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.kosa.cmsplusmain.test.dto.PostRequestDto;
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
import org.springframework.restdocs.payload.JsonFieldType;
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
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext,
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
    void getPost() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/post").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("getPost",
                        HeaderDocumentation.requestHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.ACCEPT).description("accept header")
                        ),
                        HeaderDocumentation.responseHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("name of post"),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("content of post")
                        )
                ));
    }

    @Test
    void createPost() throws Exception{
        PostRequestDto requestDto = new PostRequestDto("rlatkdtest1", "rlatkdgnstestt1");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/post")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("create Post",
                        HeaderDocumentation.requestHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.ACCEPT).description("accept header")
                        ),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("name of post"),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("content of post")

                        ),
                        HeaderDocumentation.responseHeaders(
                                HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("name of post"),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("content of post")
                        )
                ));


    }

}