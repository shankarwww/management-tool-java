package com.app.management.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.app.management.dao.AuthDao;
import com.app.management.dao.UserDao;
import com.app.management.model.UserModel;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService service;

	@Mock
	private UserDao userDao;

	@Mock
	private AuthDao authDao;

	@Mock
	private BCryptPasswordEncoder bcryptEncoder;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("before all");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("after all");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("before each");
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("after each");
	}

	@Test
	@DisplayName("Should throw the correct exception")
	void testSave() {
		Mockito.doReturn(true).when(userDao).existsById(Mockito.any());
		UserModel model = new UserModel();
		try {
			boolean save = service.save(model);
			fail("Should never reach here");
		} catch (Exception e) {
			Assertions.assertThat(e).isInstanceOf(RuntimeException.class);
			Assertions.assertThat(e.getMessage()).contains("UserId already registered:");
		}

	}

	@Test
	@DisplayName("Should throw the correct exception new way")
	void testSave_new_way() {
		Mockito.doReturn(true).when(userDao).existsById(Mockito.any());
		UserModel model = new UserModel();

		Assertions.assertThatThrownBy(() -> {
			service.save(model);
		}).isExactlyInstanceOf(RuntimeException.class).hasMessageContaining("UserId already registered");

	}

	@Test
	void testAssignRole() {
		// fail("Not yet implemented");
	}

}
