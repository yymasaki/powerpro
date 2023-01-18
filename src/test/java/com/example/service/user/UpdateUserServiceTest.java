package com.example.service.user;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.User;
import com.example.example.UserExample;
import com.example.mapper.UserMapper;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UpdateUserServiceTest {
	
	@Mock
	private UserMapper mapper;
	
	@InjectMocks
	private UpdateUserService target;
	
	@Test
	public void editUserメソッドのテスト() {
		when(mapper.updateByExampleSelective(any(User.class), any(UserExample.class))).thenReturn(true);
		User user = new User();
		user.setName("テスト太郎");
		user.setUpdatedAt(LocalDateTime.now());
		UserExample example = new UserExample();
		
		boolean isSuccess = target.editUser(user, example);
		assertTrue(isSuccess);
		verify(mapper, times(1)).updateByExampleSelective(any(User.class), any(UserExample.class));
	}

}
