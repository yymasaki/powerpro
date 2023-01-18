package com.example.service.info;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.domain.Information;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.CreateInformationForm;
import com.example.mapper.InformationMapper;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AddInformationServiceTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private AddInformationService target;
	
	@Mock
	private InformationMapper informationMapper;
	
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
	public void お知らせを追加する() throws ParseException {
		//mapper引数設定の準備開始//
		CreateInformationForm form=new CreateInformationForm();
		//target.addInformationの戻り値はinsertで生成された新しいid
		//サービスクラスのテストでは実際にDB操作は行わないため、事前にinformationIdをsetしておくことでnullPointerExceptionを避ける。
		form.setInformationId(5);
		form.setTitle("タイトル");
		form.setContent("コンテンツ");
		form.setEndPostedOn("2020/07/20");
		form.setDepartmentId("1,2,3,4,5");
		
		User user = new User();
		user.setEmail("email");
		user.setPassword("password");
		user.setUserId(1);
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		LoginUser loginUser=new LoginUser(user, authorityList);
		//mapper引数設定の準備終了//
		
		//mapperが呼ばれた回数を確認するために、formと同じ値をinformationオブジェクトに格納(113行目で活用)
		Information information = new Information();
		String startPosted="2020-06-30";
		String endPosted="2020-07-20";
		java.sql.Date startPostedOn = java.sql.Date.valueOf(startPosted);
		java.sql.Date endPostedOn = java.sql.Date.valueOf(endPosted);
		
		information.setInformationId(5);
		information.setTitle("タイトル");
		information.setContent("コンテンツ");
		information.setStartPostedOn(startPostedOn);
		information.setEndPostedOn(endPostedOn);
		information.setStage(0);
		information.setCreateUserId(1);
		Information informationForId = new Information();
		informationForId.setInformationId(5);
		
		when(informationMapper.insert(any())).thenReturn(1);
		
		target.addInformation(form, loginUser);
		verify(informationMapper,timeout(1)).insert(any()); 
		assertEquals(informationMapper.insert(any()),1);
	}

}
