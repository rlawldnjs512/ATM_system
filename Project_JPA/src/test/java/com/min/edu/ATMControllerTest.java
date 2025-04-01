package com.min.edu;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class ATMControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
//	@Test
	@DisplayName("Spring Data Rest API 실행")
	void contextLoads() throws Exception {
		this.mockMvc.perform(get("/api/machines")
				    .accept(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andDo(document("machines"))
		            .andDo(print());
	}
	
//	@ParameterizedTest
	@ValueSource(strings = {"2017"})
//	@CsvSource({"2017,ATM","2021,CD"})
	@DisplayName("ATM 생산년도로 조회하기")
	void testGetAtmByProductYear(String productYear) throws Exception {
		mockMvc.perform(get("/api/machines/search/findByProductYear?productYear="+productYear))
			   .andExpect(status().isOk())
			   .andDo(document("findByProductYear",
					   queryParameters(
							   parameterWithName("productYear").description("생산년도"))
					   ,responseFields(
							   fieldWithPath("_embedded.aTMs[].id").description("ATM의 ID값"),
							   fieldWithPath("_embedded.aTMs[].brand").description("ATM의 브랜드"),
							   fieldWithPath("_embedded.aTMs[].model").description("ATM의 모델"),
							   fieldWithPath("_embedded.aTMs[].statement").description("ATM의 영수증유무"),
							   fieldWithPath("_embedded.aTMs[].interPhone").description("ATM의 인터폰유무"),
							   fieldWithPath("_embedded.aTMs[].price").description("ATM의 가격"),
							   fieldWithPath("_embedded.aTMs[].productYear").description("ATM의 생산년도"),
							   fieldWithPath("_embedded.aTMs[]._links.self.href").description("ATM의 자체링크"),
							   fieldWithPath("_embedded.aTMs[]._links.aTM.href").description("ATM의 상세링크"),
							   fieldWithPath("_embedded.aTMs[]._links.booth.href").description("부스의 링크"),
							   fieldWithPath("_links.self.href").description("전체 요청 링크")
							   )));
	}
	
//	@ParameterizedTest
	@ValueSource(strings = {"2053"})
	@DisplayName("ATM의 부스 정보 조회")
	void getOwnerFromAtm(String atmBooth) throws Exception {
		mockMvc.perform(get("http://localhost:8088/api/machines/"+atmBooth+"/booth"))
			   .andExpect(status().isOk())
			   .andDo(document("findByBooth",
					   responseFields(
							   fieldWithPath("boothid").description("부스 아이디"),
							   fieldWithPath("type").description("부스 유형"),
							   fieldWithPath("color").description("부스 색상"),
							   fieldWithPath("_links.self.href").description("부스 정보 링크"),
							   fieldWithPath("_links.booth.href").description("부스 자체 링크"),
							   fieldWithPath("_links.atms.href").description("부스에 맞는 ATM 목록 링크")
							   )));
	}
	
//	@Test
	@DisplayName("새로운 ATM 정보 입력")
	void createMachine() throws Exception {
		String machineJson = "{"
				+ "    \"brand\" :  \"PULOON\","
				+ "    \"model\" :  \"CD\","
				+ "    \"statement\" :  \"Y\","
				+ "    \"interPhone\" :  \"N\","
				+ "    \"price\" :  \"450000\","
				+ "    \"productYear\" :  \"2018\","
				+ "    \"booth\" :  {"
				+ "        \"booth\":\"http://localhost:8088/api/booths/952\""
				+ "    }"
				+ "}"
				+ "";
		mockMvc.perform(post("http://localhost:8088/api/machines")
			   .accept(MediaType.APPLICATION_JSON)
			   .content(machineJson))
			   .andExpect(status().isCreated()) // HTTP 201 Created
			   .andDo(document("create-machine",
					   requestFields(
							   fieldWithPath("brand").description("ATM 브랜드"),
							   fieldWithPath("model").description("ATM 모델"),
							   fieldWithPath("statement").description("ATM 영수증유무"),
							   fieldWithPath("interPhone").description("ATM 인터폰유무"),
							   fieldWithPath("price").description("ATM 가격"),
							   fieldWithPath("productYear").description("ATM 생산년도"),
							   fieldWithPath("booth.booth").description("ATM 부스정보")
							   )
					  ,responseFields(
							  fieldWithPath("id").description("ATM 아이디"),
							  fieldWithPath("brand").description("ATM 브랜드"),
							  fieldWithPath("model").description("ATM 모델"),
							  fieldWithPath("statement").description("ATM 영수증유무"),
							  fieldWithPath("interPhone").description("ATM 인터폰유무"),
							  fieldWithPath("price").description("ATM 가격"),
							  fieldWithPath("productYear").description("ATM 생산년도"),
							  fieldWithPath("_links.self.href").description("부스 자체링크"),
							  fieldWithPath("_links.aTM.href").description("ATM 정보링크"),
							  fieldWithPath("_links.booth.href").description("ATM 부스 정보링크")
					   )));
	}
	
//	@Test
	@DisplayName("ATM 부스 변경 JSON/PATCH")
	void patchMachineBootJSON() throws Exception {
		String boothJson = "http://localhost:8088/api/booths/952";
		mockMvc.perform(put("http://localhost:8088/api/machines/2053/booth")
						.contentType("text/uri-list")
						.content(boothJson))
			   .andExpect(status().isNoContent())
			   .andDo(print())
			   .andDo(document("machine-booth-update"));
	}
	
//	@ParameterizedTest
	@ValueSource(strings = {"2952"})
	@DisplayName("ATM 아이디를 통한 삭제")
	void deleteMachines(String delAtmId) throws Exception {
//		mockMvc.perform(delete("http://localhost:8088/api/machines/"+delAtmId))
		mockMvc.perform(delete("http://localhost:8088/api/machines/{id}",delAtmId))
			   .andExpect(status().isNoContent())
			   .andDo(document("delete-machine",
					   pathParameters(
							   parameterWithName("id").description("삭제할 ATM 아이디")
							   )));
	}
	
	
	

}
