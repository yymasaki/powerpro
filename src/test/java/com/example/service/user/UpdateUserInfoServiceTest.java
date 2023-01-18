package com.example.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.example.domain.User;
import com.example.example.UserExample;
import com.example.form.EditUserForm;
import com.example.mapper.UserMapper;

@SpringBootTest
public class UpdateUserInfoServiceTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private UpdateUserInfoService target;

	@Mock
	private UserMapper userMapper;

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
	public void ユーザー情報を更新する() {
		EditUserForm form = new EditUserForm();
		form.setUserId(1);
		form.setLastName("太郎");
		form.setFirstName("テスト");
		form.setDepartmentId("1");
		form.setHiredOn("2020-01-01");
		form.setEmail("test.taro");
		form.setVersion("1");
		String updator = "テスト太郎";
		User user = new User();
		user.setVersion(1);
		when(userMapper.selectByPrimaryKey(any())).thenReturn(user);
		assertEquals(true, target.update(form, updator));
		verify(userMapper, times(1)).updateByExampleSelective(any(User.class),any(UserExample.class));
	}
	
	@Test
	public void versionが既に更新されていた場合はユーザー情報を更新不可() {
		EditUserForm form = new EditUserForm();
		form.setUserId(1);
		form.setLastName("太郎");
		form.setFirstName("テスト");
		form.setDepartmentId("1");
		form.setHiredOn("2020-01-01");
		form.setEmail("test.taro");
		form.setVersion("1");
		String updator = "テスト太郎";
		User user = new User();
		user.setVersion(2);
		when(userMapper.selectByPrimaryKey(any())).thenReturn(user);
		assertEquals(false, target.update(form, updator));
		verify(userMapper, times(0)).updateByExampleSelective(any(User.class),any(UserExample.class));
	}

}
