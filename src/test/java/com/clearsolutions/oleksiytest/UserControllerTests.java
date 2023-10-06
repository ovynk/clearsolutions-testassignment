package com.clearsolutions.oleksiytest;

import com.clearsolutions.oleksiytest.controller.UserController;
import com.clearsolutions.oleksiytest.model.User;
import com.clearsolutions.oleksiytest.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@EnableWebMvc
public class UserControllerTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mvc;

    User userFromService = new User(
            "vynokurovoleksiy@gmail.com",
            "Oleksiy",
            "Vynokurov",
            LocalDate.of(2000, 1, 1),
            null,
            null
    );

    @Test
    public void createUser() throws Exception {
        User user = new User(
                "test1@gmail.com",
                "Test",
                "Test",
                LocalDate.of(2000, 10, 1),
                null,
                null
        );

        when(userService.create(user)).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = post("http://localhost:8080/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user));

        mvc.perform(mockRequest).andExpect(status().isCreated());
    }

    @Test
    public void readUserTest() throws Exception {
        when(userService.findByEmail("vynokurovoleksiy@gmail.com")).thenReturn(userFromService);

        mvc.perform(get("http://localhost:8080/api/users/vynokurovoleksiy@gmail.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "{" +
                        "\"email\":\"vynokurovoleksiy@gmail.com\"," +
                        "\"firstName\":\"Oleksiy\"," +
                        "\"lastName\":\"Vynokurov\"," +
                        "\"birthDate\":\"2000-01-01\"," +
                        "\"address\":null," +
                        "\"phoneNumber\":null," +
                        "\"_links\":{\"self\":{\"href\":\"http://localhost:8080/api/users/vynokurovoleksiy@gmail.com\"}}}"));
    }

    @Test
    public void updateUser() throws Exception {
        User userUpdated = new User(
                "test1@gmail.com",
                "Mark",
                "Rents",
                LocalDate.of(2000, 1, 1),
                "Edinburgh",
                null
        );

        when(userService.update(userUpdated)).thenReturn(userUpdated);

        MockHttpServletRequestBuilder mockRequest = put("http://localhost:8080/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userUpdated));

        mvc.perform(mockRequest).andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        User user = new User(
                "test1@gmail.com",
                "Mark",
                "Rents",
                LocalDate.of(2000, 1, 1),
                "Edinburgh",
                null
        );

        when(userService.findByEmail(user.getEmail())).thenReturn(user);

        mvc.perform(delete("http://localhost:8080/api/users/test1@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}