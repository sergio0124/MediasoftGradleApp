package reznikov.sergey.MediasoftGradleApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reznikov.sergey.MediasoftGradleApp.model.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MediasoftGradleAppApplicationTests {

	@Test
	void userToStringTest() throws IOException {

		User user = new User("username", "password");
		String toStringResult = "User{id=null, username='username', password='password', roles=null, sentMessages=null, receivedMessages=null}";
		assertEquals(toStringResult, user.toString());

	}

}
