package com.example.controller.common;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowLoginPageControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private ShowLoginPageController target;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void ログイン画面を表示する() throws Exception {
		MvcResult result = mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("common/login"))
				.andReturn();
		assertTrue(result.getModelAndView().getModel().isEmpty());
	}

	@Test
	public void ログイン失敗時にログイン画面を表示する() throws Exception {
		MvcResult result = mockMvc.perform(get("/login?error=true"))
				.andExpect(status().isOk())
				.andExpect(view().name("common/login"))
				.andReturn();
		String expectedMessage = "メールアドレスまたはパスワードが不正です。";
		String actualMessage = (String) result.getModelAndView().getModel().get("errorMessage");
		assertEquals(expectedMessage, actualMessage);
	}
	
	/**
	 * @author nonaka
	 */
	@Test
	public void セッションが存在する場合のログイン画面表示() throws Exception{
		MockHttpServletRequest request = new MockHttpServletRequest();
        PortResolver portResolver = mock(PortResolver.class);
		MockHttpSession session = new MockHttpSession();
		request.setSecure(false);
		request.setScheme("http");
		request.setServerName("localhost");
		request.setRequestURI("/login");
		SavedRequest savedRequest = new DefaultSavedRequest(request, portResolver);
		session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest);
		request.setSession(session);
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/login")
				.param("req", String.valueOf(request))
				.session(session);
		
		mockMvc.perform(getRequest)
				.andExpect(status().isOk())
				.andExpect(view().name("common/login"))
				.andReturn();
		
		HttpSession sessionForTest = request.getSession();
		assertEquals("url", savedRequest.getRedirectUrl(),sessionForTest.getAttribute("url"));
	}
	
	/**
	 * @author nonaka
	 */
	@Test
	public void urlにhtmlが存在する場合のログイン画面表示() throws Exception{
		MockHttpServletRequest request = new MockHttpServletRequest();
		PortResolver portResolver = mock(PortResolver.class);
		MockHttpSession session = new MockHttpSession();
		request.setSecure(false);
		request.setScheme("http");
		request.setServerName("localhost");
		request.setRequestURI("/html");
		SavedRequest savedRequest = new DefaultSavedRequest(request, portResolver);
		session.setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest);
		request.setSession(session);
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/login")
				.param("req", String.valueOf(request))
				.session(session);
		
		mockMvc.perform(getRequest)
		.andExpect(status().isOk())
		.andExpect(view().name("common/login"))
		.andReturn();
	}

	/**
	 * @author nonaka
	 */
	@Test
	public void savedRequestがnullの時のログイン画面表示() throws Exception{
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		request.setSecure(false);
		request.setScheme("http");
		request.setServerName("localhost");
		request.setServerPort(8080);
		request.setRequestURI("/static");
		request.setSession(session);
		
		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get("/login")
				.param("req", String.valueOf(request))
				.session(session);
		
		mockMvc.perform(getRequest)
		.andExpect(status().isOk())
		.andExpect(view().name("common/login"))
		.andReturn();
	}
}