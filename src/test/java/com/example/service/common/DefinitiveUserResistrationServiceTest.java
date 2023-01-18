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
import com.example.form.RegisterUserForm;
import com.example.mapper.UserMapper;

@SpringBootTest
public class DefinitiveUserResistrationServiceTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private DefinitiveUserResistrationService target;

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
	public void 仮ユーザーの本登録をする() {
		RegisterUserForm form = new RegisterUserForm();
		form.setDepartmentId("1");
		form.setLastName("テスト");
		form.setFirstName("太郎");
		form.setEmail("junit.test");
		form.setHiredOn("2020-01-01");
		form.setPassword("password");
		form.setCheckPassword("password");

		target.definitiveResistration(form);
		verify(userMapper, times(1)).updateNewestTemporaryUser(any(User.class));
	}

}
