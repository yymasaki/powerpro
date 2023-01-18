package com.example.service.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import com.example.example.UserExample;
import com.example.mapper.UserMapper;

@SpringBootTest
public class GetUserByUpdaterServiceTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private GetUserByUpdaterService target;

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
	public void データ更新者とデータ状況でユーザー情報を取得する() {
		String updater = "updater";
		String stage = "0";

		List<User> expectedUserList = new ArrayList<>();
		User user = new User();
		user.setName("テスト太郎");
		expectedUserList.add(user);
		when(userMapper.selectByExample(any(UserExample.class))).thenReturn(expectedUserList);

		List<User> userListActual = target.getUserByUpdaterAndStage(updater, stage);
		verify(userMapper, times(1)).selectByExample(any(UserExample.class));
		assertEquals(expectedUserList, userListActual);
	}

}
