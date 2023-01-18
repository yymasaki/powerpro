package com.example.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import com.example.form.SearchUserForm;
import com.example.mapper.UserMapper;

@SpringBootTest
public class GetUsersServiceTest {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@InjectMocks
	private GetUsersService target;

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
	public void 検索条件に一致するユーザー情報を取得する() {
		SearchUserForm form = new SearchUserForm();
		form.setDepartmentId("1");
		form.setHiredOn("2020-01-01");
		form.setName("テスト太郎");
		String[] skills = { "1", "2" };
		form.setSkills(skills);
		target.get(form);

		List<User> userList = new ArrayList<>();
		User user1 = new User();
		userList.add(user1);
		User user2 = new User();
		userList.add(user2);
		User user3 = new User();
		userList.add(user3);
		when(userMapper.selectUsersWithTechnicalSkills(any(User.class), any())).thenReturn(userList);
		
		assertEquals(userList, target.get(form));
	}

}
