package com.example.service.spec;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.common.Stage;
import com.example.domain.User;
import com.example.service.user.GetSpecificUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendSpecMailServiceTest {
	
	@Mock
	private GetSpecificUserService getSpecificUserService;
	@Mock
	private MailSender mailSender;
	@Mock
	private HttpServletRequest request;
	@InjectMocks
	private SendSpecMailService service;

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
	public void TC1_申請を管理者が承認した際のメール送信() {
		String process = "申請承認";
		Integer specsheetId = 1;
		Integer userId = 1;
		Integer stage = Stage.ACTIVE.getKey();
		
		User user = new User();
		user.setUserId(1);
		user.setName("test");
		user.setEmail("a@b.com");
		
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(user);
		when(request.getRequestURL()).thenReturn(new StringBuffer("https://powerpro.com/request/spec/edit/do"));
		when(request.getRequestURI()).thenReturn("/request/spec/edit/do");
		service.sendSpecMail(process, "", specsheetId, userId, stage);
		verify(getSpecificUserService, times(1)).get(any(Integer.class));
		verify(request, times(1)).getRequestURL();
		verify(request, times(1)).getRequestURI();
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	@Test
	public void TC2_申請を管理者が否承認した際のメール送信() {
		String process = "申請否承認";
		String adminComment = "否承認しました";
		Integer specsheetId = 1;
		Integer userId = 1;
		Integer stage = Stage.SENT_BACK.getKey();
		
		User user = new User();
		user.setUserId(1);
		user.setName("test");
		user.setEmail("a@b.com");
		
		when(getSpecificUserService.get(any(Integer.class))).thenReturn(user);
		when(request.getRequestURL()).thenReturn(new StringBuffer("https://powerpro.com/request/spec/edit/do"));
		when(request.getRequestURI()).thenReturn("/request/spec/edit/do");
		service.sendSpecMail(process, adminComment, specsheetId, userId, stage);
		verify(getSpecificUserService, times(1)).get(any(Integer.class));
		verify(request, times(1)).getRequestURL();
		verify(request, times(1)).getRequestURI();
		verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
	}

}
