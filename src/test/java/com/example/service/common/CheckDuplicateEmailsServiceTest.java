package com.example.service.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.User;
import com.example.mapper.UserMapper;

@SpringBootTest
public class CheckDuplicateEmailsServiceTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private CheckDuplicateEmailsService target;

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
	public void メールアドレスがDBに登録済の場合trueを返す() {
		String email = "junit.test";
		List<User> users = new ArrayList<>();
		users.add(new User());
		when(userMapper.selectByEmailAndStage(email, "0")).thenReturn(users);
		assertEquals(true, target.checkDuplication(email));
		verify(userMapper, times(1)).selectByEmailAndStage(email, "0");
	}

	@Test
	public void メールアドレスがDBに未登録の場合falseを返す() {
		String email = "junit.test";
		List<User> users = new ArrayList<>();
		when(userMapper.selectByEmailAndStage(email, "0")).thenReturn(users);
		assertEquals(false, target.checkDuplication(email));
		verify(userMapper, times(1)).selectByEmailAndStage(email, "0");
	}

}
