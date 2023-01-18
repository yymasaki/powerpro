package com.example.controller.common;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.domain.Information;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.service.info.GetInformationListService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShowInfomationListControllerTest {

	private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    private AdminloginUser testUser;
	
    @MockBean
	private GetInformationListService getInformationListService;

	@InjectMocks
	private ShowInfomationListController target;


	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();

	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	/**
	 * @author namikitsubasa
	 */
	@Test
	public void トップ画面を表示する_お知らせ0件() throws Exception {
		List<Information> informationList = new ArrayList<>();
		List<Information> allInformationList = new ArrayList<>();
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		when(getInformationListService.getInformationList(any(), any())).thenReturn(informationList); 
		doReturn(allInformationList).when(getInformationListService).getAllInformationList(any());
		
		//テスト対象のメソッドを実行し、以下項目検証
		MvcResult result = mockMvc.perform(get("/")
		.with(user(new LoginUser(loginUser,authorityList))))
		.andExpect(status().isOk())
		.andExpect(view().name("common/top"))
		.andReturn();
		
		//モデルスコープに想定の値が格納されているか検証
		assertEquals(result.getModelAndView().getModel().get("offset"), 0);
		assertEquals(result.getModelAndView().getModel().get("informationListSize"), 0);
		assertEquals(result.getModelAndView().getModel().get("informationList"),informationList);
		assertEquals(result.getModelAndView().getModel().get("numOfPage"),1);
		assertEquals(result.getModelAndView().getModel().get("currentPage"),1);
		assertEquals(result.getModelAndView().getModel().get("noInformation"),"お知らせはありません。");
		 verify(getInformationListService, times(1)).getInformationList(any(), any());
		 verify(getInformationListService, times(1)).getInformationList(any(), any());
	}
	
	/**
	 * @author namikitsubasa
	 */
	@Test
	public void トップ画面を表示する_お知らせ情報あり() throws Exception {
		List<Information> informationList = new ArrayList<>();
		List<Information> allInformationList = new ArrayList<>();
		Information information1=new Information();
		Information information2=new Information();
		Information information3=new Information();
		Information information4=new Information();
		Information information5=new Information();
		information1.setInformationId(1);
		information1.setTitle("タイトル");
		information1.setContent("コンテンツ");
		information1.setStage(0);
		information1.setStartPostedOn(Date.valueOf("2020-07-20"));
		information1.setEndPostedOn(Date.valueOf("2025-07-20"));
		information2.setContent("コンテンツ");
		information3.setContent("コンテンツ");
		information4.setContent("コンテンツ");
		information5.setContent("コンテンツ");
		informationList.add(information1);
		informationList.add(information2);
		informationList.add(information3);
		informationList.add(information4);
		informationList.add(information5);
		allInformationList.add(information1);
		allInformationList.add(information2);
		allInformationList.add(information3);
		allInformationList.add(information4);
		allInformationList.add(information5);
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		when(getInformationListService.getInformationList(any(), any())).thenReturn(informationList); 
		doReturn(allInformationList).when(getInformationListService).getAllInformationList(any());
		
		//テスト対象のメソッドを実行し、以下項目検証
		MvcResult result = mockMvc.perform(get("/")
				.with(user(new LoginUser(loginUser,authorityList)))
				.param("offset", "0"))
				.andExpect(status().isOk())
				.andExpect(view().name("common/top"))
				.andReturn();
		
		//モデルスコープに想定の値が格納されているか検証
		assertEquals(result.getModelAndView().getModel().get("offset"), 0);
		assertEquals(result.getModelAndView().getModel().get("informationListSize"), 5);
		assertEquals(result.getModelAndView().getModel().get("informationList"),informationList);
		assertEquals(result.getModelAndView().getModel().get("numOfPage"),1);
		assertEquals(result.getModelAndView().getModel().get("currentPage"),1);
		verify(getInformationListService, times(1)).getInformationList(any(), any());
		 verify(getInformationListService, times(1)).getInformationList(any(), any());
	}
	
	/**
	 * 
	 * information.size()の分岐チェック
	 * 
	 * @author namikitsubasa
	 */
	@Test
	public void トップ画面を表示する_if文チェック_TC1() throws Exception {
		
		//if文機能チェックのため、informationを6つ(境界値)作成(informationList.size=6)
		List<Information> informationList = new ArrayList<>();
		List<Information> allInformationList = new ArrayList<>();
		Information information1=new Information();
		information1.setContent("コンテンツ");
		Information information2=new Information();
		information2.setContent("コンテンツ");
		Information information3=new Information();
		information3.setContent("コンテンツ");
		Information information4=new Information();
		information4.setContent("コンテンツ");
		Information information5=new Information();
		information5.setContent("コンテンツ");
		Information information6=new Information();
		information6.setContent("コンテンツ");
		informationList.add(information1);
		informationList.add(information2);
		informationList.add(information3);
		informationList.add(information4);
		informationList.add(information5);
		informationList.add(information6);
		allInformationList.add(information1);
		allInformationList.add(information2);
		allInformationList.add(information3);
		allInformationList.add(information4);
		allInformationList.add(information5);
		allInformationList.add(information6);
		
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		when(getInformationListService.getInformationList(any(), any())).thenReturn(informationList); 
		doReturn(allInformationList).when(getInformationListService).getAllInformationList(any());
		
		//テスト対象のメソッドを実行し、以下項目検証
		MvcResult result=mockMvc.perform(get("/")
				.with(user(new LoginUser(loginUser,authorityList)))
				//offsetは0未満の場合,if文で0に変更するため、-1を格納することで検証
				.param("offset", "3"))
				.andExpect(status().isOk())
				.andExpect(view().name("common/top"))
				.andReturn();
		
		//モデルスコープに想定の値が格納されているか検証
		//offset<0 , informationListSize=6の場合、コントローラ内でそれぞれoffset=0,informationListSize=5に変更させる
		assertEquals(result.getModelAndView().getModel().get("offset"), 0);
		assertEquals(result.getModelAndView().getModel().get("informationList"),informationList);
		//モデルスコープにはif文でinformationListSizeを変更させる前のsizeを格納する(ページングの都合上)
		assertEquals(result.getModelAndView().getModel().get("informationListSize"),6);
		assertEquals(informationList.size(),5);
		verify(getInformationListService, times(1)).getInformationList(any(), any());
		verify(getInformationListService, times(1)).getInformationList(any(), any());
	}
	
	
	/**
	 * 
	 * offsetを手動で実際のページ数よりも大きい数字を入力された場合のif分機能チェック
	 * 閲覧ページ番号currentPage=総ページ数numOfPageとなる
	 * 
	 * @author namikitsubasa
	 */
	@Test
	public void トップ画面を表示する_if文チェック_TC2() throws Exception {
		
		//if文機能チェックのため、informationを6つ(境界値)作成(informationList.size=6)
		List<Information> allInformationList = new ArrayList<>();
		List<Information> informationList = new ArrayList<>();
		//ログイン認証用の情報
		Collection<GrantedAuthority> authorityList=testUser.getAuthorityList();
		User loginUser=testUser.getUser();
		
		when(getInformationListService.getInformationList(any(), any())).thenReturn(informationList); 
		doReturn(allInformationList).when(getInformationListService).getAllInformationList(any());
		//テスト対象のメソッドを実行し、以下項目検証
		MvcResult result=mockMvc.perform(get("/")
				.with(user(new LoginUser(loginUser,authorityList)))
				//offsetは0未満の場合,if文で0に変更するため、-1を格納することで検証
				.param("offset", "20"))
				.andExpect(status().isOk())
				.andExpect(view().name("common/top"))
				.andReturn();
		
		//モデルスコープに想定の値が格納されているか検証
		assertEquals(result.getModelAndView().getModel().get("offset"), 20);
		assertEquals(result.getModelAndView().getModel().get("informationList"),informationList);
		assertEquals(result.getModelAndView().getModel().get("numOfPage"),1);
		assertEquals(result.getModelAndView().getModel().get("currentPage"),1);
		//モデルスコープにはif文でinformationListSizeを変更させる前のsizeを格納する(ページングの都合上)
		assertEquals(result.getModelAndView().getModel().get("informationListSize"),0);
		assertEquals(informationList.size(),0);
		verify(getInformationListService, times(1)).getAllInformationList(any());
		verify(getInformationListService, times(1)).getInformationList(any(), any());
	}
	
}
