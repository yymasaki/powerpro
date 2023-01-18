package com.example.service.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.User;
import com.example.loginUserSample.AdminloginUser;
import com.example.loginUserSample.ClLoginUser;
import com.example.loginUserSample.MlLoginUser;
import com.example.loginUserSample.SalesLoginUser;
import com.example.loginUserSample.WebLoginUser;
import com.example.mapper.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetailsServiceImplTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private UserDetailsServiceImpl target;

	@Mock
	private UserMapper userMapper;

	@Autowired
	private AdminloginUser adminTestUser;

	@Autowired
	private SalesLoginUser salesTestUser;

	@Autowired
	private WebLoginUser webTestUser;

	@Autowired
	private ClLoginUser clTestUser;

	@Autowired
	private MlLoginUser mlTestUser;


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
	public void 管理者権限でログイン認証処理を行う() {
		User testUser = adminTestUser.getUser();
		List<User> users = new ArrayList<>();
		users.add(testUser);
		when(userMapper.selectByEmailAndStage(testUser.getEmail(), "0")).thenReturn(users);
		UserDetails loginUser = target.loadUserByUsername(testUser.getEmail());
		assertEquals("[ROLE_ADMIN]", String.valueOf(loginUser.getAuthorities()));

		// Mock化されたオブジェクトの呼び出し検証
		verify(userMapper, times(1)).selectByEmailAndStage(testUser.getEmail(), "0");
	}

	@Test
	public void 営業権限でログイン認証処理を行う() {
		User testUser = salesTestUser.getUser();
		List<User> users = new ArrayList<>();
		users.add(testUser);
		when(userMapper.selectByEmailAndStage(testUser.getEmail(), "0")).thenReturn(users);
		UserDetails loginUser = target.loadUserByUsername(testUser.getEmail());
		assertEquals("[ROLE_SALES]", String.valueOf(loginUser.getAuthorities()));

		// Mock化されたオブジェクトの呼び出し検証
		verify(userMapper, times(1)).selectByEmailAndStage(testUser.getEmail(), "0");
	}

	@Test
	public void WEB権限でログイン認証処理を行う() {
		User testUser = webTestUser.getUser();
		List<User> users = new ArrayList<>();
		users.add(testUser);
		when(userMapper.selectByEmailAndStage(testUser.getEmail(), "0")).thenReturn(users);
		UserDetails loginUser = target.loadUserByUsername(testUser.getEmail());
		assertEquals("[ROLE_WEB]", String.valueOf(loginUser.getAuthorities()));

		// Mock化されたオブジェクトの呼び出し検証
		verify(userMapper, times(1)).selectByEmailAndStage(testUser.getEmail(), "0");
	}

	@Test
	public void CL権限でログイン認証処理を行う() {
		User testUser = clTestUser.getUser();
		List<User> users = new ArrayList<>();
		users.add(testUser);
		when(userMapper.selectByEmailAndStage(testUser.getEmail(), "0")).thenReturn(users);
		UserDetails loginUser = target.loadUserByUsername(testUser.getEmail());
		assertEquals("[ROLE_CL]", String.valueOf(loginUser.getAuthorities()));

		// Mock化されたオブジェクトの呼び出し検証
		verify(userMapper, times(1)).selectByEmailAndStage(testUser.getEmail(), "0");
	}

	@Test
	public void ML権限でログイン認証処理を行う() {
		User testUser = mlTestUser.getUser();
		List<User> users = new ArrayList<>();
		users.add(testUser);
		when(userMapper.selectByEmailAndStage(testUser.getEmail(), "0")).thenReturn(users);
		UserDetails loginUser = target.loadUserByUsername(testUser.getEmail());
		assertEquals("[ROLE_ML]", String.valueOf(loginUser.getAuthorities()));

		// Mock化されたオブジェクトの呼び出し検証
		verify(userMapper, times(1)).selectByEmailAndStage(testUser.getEmail(), "0");
	}

	@Test
	public void ログイン認証失敗の場合は例外発生() {
		User testUser = mlTestUser.getUser();
		when(userMapper.selectByEmailAndStage(testUser.getEmail(), "0")).thenReturn(null);
		Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
			target.loadUserByUsername(testUser.getEmail());
		});
		assertEquals("not found : " + testUser.getEmail(), exception.getMessage());
	}
}
