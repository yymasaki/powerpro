package com.example.service.user;

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
import com.example.form.AddUserForm;
import com.example.mapper.UserMapper;

@SpringBootTest
public class AddNewUserServiceTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private AddNewUserService target;

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
	public void 新規ユーザー登録する() {
		AddUserForm form = new AddUserForm();
		form.setLastName("太郎");
		form.setFirstName("テスト");
		form.setDepartmentId("1");
		form.setHiredOn("2020-01-01");
		form.setPassword("password");
		form.setCheckPassword("password");
		String creator = "テスト次郎";
		target.add(form, creator);
		verify(userMapper, times(1)).insertSelective(any(User.class));
	}

}
