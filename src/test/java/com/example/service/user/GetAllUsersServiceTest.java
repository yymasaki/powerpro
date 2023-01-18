package com.example.service.user;

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
public class GetAllUsersServiceTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private GetAllUsersService target;

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
	public void すべての有効ユーザー情報を取得する() {
		List<User> userList = new ArrayList<>();
		User user1 = new User();
		userList.add(user1);
		User user2 = new User();
		userList.add(user2);
		User user3 = new User();
		userList.add(user3);
		when(userMapper.selectAllValidUsers()).thenReturn(userList);
		List<User> actualList = target.getAllUsers();
		verify(userMapper, times(1)).selectAllValidUsers();
		assertEquals(userList, actualList);
		
	}

}
