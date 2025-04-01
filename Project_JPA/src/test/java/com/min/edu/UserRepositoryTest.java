package com.min.edu;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class UserRepositoryTest {

	@Autowired
	private MockMvc mockMvc;
	
//	@Test
	public void loginSessionTest() throws Exception {
		Map<String,String> loginRequest = new HashMap<String,String>();
		loginRequest.put("username", "user");
		loginRequest.put("password", "testuser");
		
		MvcResult result = mockMvc.perform(post("/login")
			   .with(csrf())
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			   .content("username="+loginRequest.get("username")
			   			+"&password="+loginRequest.get("password"))
			   .with(httpBasic(loginRequest.get("username"), loginRequest.get("password"))))
			   .andExpect(status().is3xxRedirection())
			   .andReturn();
		
		String rediectedUrl = result.getResponse().getHeader("location");
		System.out.println("redirected to : "+rediectedUrl); // "/"
		
		MockHttpSession session = (MockHttpSession)result.getRequest().getSession();
		SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		assertNotNull(securityContext);
		assertTrue(securityContext.getAuthentication() != null);
		assertEquals("user", securityContext.getAuthentication().getName());
		
		System.out.println(securityContext);
		System.out.println(securityContext.getAuthentication());
		System.out.println(securityContext.getAuthentication().getName());
		
		mockMvc.perform(get("/api")
			   .contentType(MediaType.APPLICATION_JSON)
			   .session(session)
			   .accept(MediaType.APPLICATION_JSON)
			   .with(csrf()))
		       .andExpect(status().isOk())
		       .andDo(document("after_login_api",
		    		   responseFields(
		    				   fieldWithPath("_links").description("전달받은 resource"),
		    				   fieldWithPath("_links.booths.href").description("부스 링크"),
		    				   fieldWithPath("_links.booths.templated").description("부스 링크 templated"),
		    				   fieldWithPath("_links.aTMs.href").description("ATM 링크"),
		    				   fieldWithPath("_links.aTMs.templated").description("ATM 링크 templated"),
		    				   fieldWithPath("_links.profile.href").description("프로파일 링크")
		    				   )))
		       .andDo(print());
	}
	
	
	
	
	
	
	

}
