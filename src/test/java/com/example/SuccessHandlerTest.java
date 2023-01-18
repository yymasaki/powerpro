package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuccessHandlerTest {
	
	@Autowired
	private SuccessHandler target;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void urlが入力されている場合の画面遷移() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("url", "http://localhost:8080/skill/status");
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		target.onAuthenticationSuccess(request, response, null);
	}
	
	@Test
	public void urlがnullの場合の画面遷移() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("url", null);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		target.onAuthenticationSuccess(null, response, null);
	}

}
