package com.clearsolutions.oleksiytest.service;

import com.clearsolutions.oleksiytest.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User create(User user);

    User findByEmail(String email);

    User update(User user);

    void delete(User user);

    List<User> getUsersByBirthRange(LocalDate from, LocalDate to);

}
