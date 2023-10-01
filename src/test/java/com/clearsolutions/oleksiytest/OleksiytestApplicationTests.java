package com.clearsolutions.oleksiytest;

import com.clearsolutions.oleksiytest.model.User;
import com.clearsolutions.oleksiytest.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OleksiytestApplicationTests {

    @InjectMocks
    private static UserServiceImpl userService;


    @Test
    void contextLoads() {
    }

    @Test
    void testAddUser() {
        User expected = new User(
                "test@gmail.com",
                "Oleksiy",
                "Vynokurov",
                LocalDate.of(2000, 1, 1),
                null,
                null);

        User actual = userService.create(expected);

        Assertions.assertEquals(expected, actual, "Failed to add new user");
    }

}
