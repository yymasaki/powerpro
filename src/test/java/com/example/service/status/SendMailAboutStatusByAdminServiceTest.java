package com.example.service.status;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.common.Stage;
import com.example.domain.User;
import com.example.mapper.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendMailAboutStatusByAdminServiceTest{
	
	@Mock
	private UserMapper mapper;
	
	@Mock
	private MailSender sender;
	
	@Mock
	private HttpServletRequest request;
	
	@InjectMocks
	private SendMailAboutStatusByAdminService target;

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
	public void ステージ0でメールを送信する() {
		Integer userId = 1;
		Integer statusId = 1;
		User user = new User();
		user.setUserId(userId);
		user.setName("テスト太郎");
		user.setEmail("test");
		StringBuffer url = new StringBuffer();
		url.append("http://sample.com/");
		when(mapper.selectByPrimaryKey(userId)).thenReturn(user);
		when(request.getRequestURL()).thenReturn(url);
		target.sendMailAboutStatusByAdmin(userId, Stage.ACTIVE.getKey(), "テストです", statusId, "申請"+Stage.of(Stage.ACTIVE.getKey()).getMessageForRequest());
		
		verify(mapper, times(1)).selectByPrimaryKey(userId);
		verify(request, times(1)).getRequestURL();
	}
	
	@Test
	public void ステージ3でメールを送信する() {
		Integer userId = 1;
		Integer statusId = 1;
		User user = new User();
		user.setUserId(userId);
		user.setName("テスト太郎");
		user.setEmail("test");
		StringBuffer url = new StringBuffer();
		url.append("http://sample.com/");
		when(mapper.selectByPrimaryKey(userId)).thenReturn(user);
		when(request.getRequestURL()).thenReturn(url);
		target.sendMailAboutStatusByAdmin(userId, Stage.SENT_BACK.getKey(), "テストです", statusId, "申請"+Stage.of(Stage.SENT_BACK.getKey()).getMessageForRequest());
		
		verify(mapper, times(1)).selectByPrimaryKey(userId);
		verify(request, times(1)).getRequestURL();
	}
	
	@Test
	public void ステージ9でメールを送信する() {
		Integer userId = 1;
		Integer statusId = 1;
		User user = new User();
		user.setUserId(userId);
		user.setName("テスト太郎");
		user.setEmail("test");
		StringBuffer url = new StringBuffer();
		url.append("http://sample.com/");
		when(mapper.selectByPrimaryKey(userId)).thenReturn(user);
		when(request.getRequestURL()).thenReturn(url);
		target.sendMailAboutStatusByAdmin(userId, Stage.DELETED.getKey(), "テストです", statusId, "申請"+Stage.of(Stage.DELETED.getKey()).getMessageForRequest());
		
		verify(mapper, times(1)).selectByPrimaryKey(userId);
		verify(request, times(1)).getRequestURL();
	}

}
