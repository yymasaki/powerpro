package com.example.service.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@SpringBootTest
public class SendFormUrlByEmailServiceTest {
	
	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private SendFormUrlByEmailService target;
	
	@Mock
	private MailSender mailSender;

	@Mock
	private HttpServletRequest httpServletRequest;

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
	public void 会員登録メールを送信する() {
		// モック戻り値指定
		StringBuffer url = new StringBuffer();
		url.append("http://sample.com/");
		when(httpServletRequest.getRequestURL()).thenReturn(url);
		
		target.sendUrl("/register", "sampleId" , "mayumi.ono@rakus-partners.co.jp");
		
		// mock化メソッドの呼び出し確認
		verify(httpServletRequest, times(1)).getRequestURL();
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
	}

	@Test
	public void パスワード再設定メールを送信する() {
		// モック戻り値指定
		StringBuffer url = new StringBuffer();
		url.append("http://sample.com/");
		when(httpServletRequest.getRequestURL()).thenReturn(url);
		
		target.sendUrl("/pass/reset", "sampleId" , "mayumi.ono@rakus-partners.co.jp");
		
		// mock化メソッドの呼び出し確認
		verify(httpServletRequest, times(1)).getRequestURL();
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
	}

}
