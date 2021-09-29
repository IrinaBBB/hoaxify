package ru.aurorahost.hoaxify;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpStatusCodeException;
import ru.aurorahost.hoaxify.shared.GenericResponse;
import ru.aurorahost.hoaxify.user.User;
import ru.aurorahost.hoaxify.user.UserRepository;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    public static final String API_1_0_USERS = "/api/1.0/users";
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserRepository userRepository;

    @Before
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void postUser_whenUserIsValid_receiveOk() {
        // Arrange
        User user = createValidUser();

        // Act
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase() {
        // Arrange
        User user = createValidUser();

        // Act
        testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);

        // Assert
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void postUser_whenUserIsValid_receiveSuccessMessage() {
        // Arrange
        User user = createValidUser();

        // Act
        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_1_0_USERS, user,
                GenericResponse.class);
        // Assert
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isNotNull();
    }

    @Test
    public void postUser_whenUserIsValid_passwordIsHashedInDatabase() {
        // Arrange
        User user = createValidUser();

        // Act
        testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        List<User> users = userRepository.findAll();
        User inDb = users.get(0);

        // Assert
        assertThat(inDb.getPassword()).isNotEqualTo(user.getPassword());

    }

    private User createValidUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");
        return user;
    }
}
