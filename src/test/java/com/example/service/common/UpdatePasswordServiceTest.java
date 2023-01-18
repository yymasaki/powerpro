package com.example.service.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import com.example.mapper.UserMapper;

@SpringBootTest
public class UpdatePasswordServiceTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private UpdatePasswordService target;

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
	public void パスワードを更新する() {
		target.update(1, "password", "テスト太郎");
		verify(userMapper, times(1)).updateByExampleSelective(any(User.class), any(UserExample.class));
	}

}
