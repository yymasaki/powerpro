package com.example.service.common;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import com.example.mapper.UserMapper;

@SpringBootTest
public class SetUuidOnUpdaterInUsersInfoServiceTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private SetUuidOnUpdaterInUsersInfoService target;

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
	public void emailに該当するユーザー情報の更新者をUUIDにする() {
		String email = "junit.insert.test";
		String uuid = "sampleSessionId";
		
		when(userMapper.updateByExampleSelective(any(User.class), any(UserExample.class))).thenReturn(true);
		assertEquals(true, target.setUuidAsUpdater(uuid, email));
	}

}
