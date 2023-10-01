package com.clearsolutions.oleksiytest.service.impl;

import com.clearsolutions.oleksiytest.model.User;
import com.clearsolutions.oleksiytest.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> users = new ArrayList<>();

    {
        users.add(new User(
                "vynokurovoleksiy@gmail.com",
                "Oleksiy",
                "Vynokurov",
                LocalDate.of(2000, 1, 1),
                null,
                null)
        );

        users.add(new User(
                "alexa@gmail.com",
                "Alex",
                "Aaron",
                LocalDate.of(1997, 9, 9),
                "NYC",
                "+1 808 191-9982")
        );
    }

    @Override
    public User create(User user) {
        if (findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists.");
        }

        User newUser = new User(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getAddress(),
                user.getPhoneNumber()
        );

        users.add(newUser);
        return newUser;
    }

    @Override
    public User findByEmail(String email) {
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User update(User user) {
        User userToUpdate = findByEmail(user.getEmail());

        if (userToUpdate != null) {
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setBirthDate(user.getBirthDate());
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setPhoneNumber(user.getPhoneNumber());
            return userToUpdate;
        }
        return null;
    }

    @Override
    public void delete(User user) {
        User userToDelete = findByEmail(user.getEmail());
        users.remove(userToDelete);
    }

    @Override
    public List<User> getUsersByBirthRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new RuntimeException("\"from\" must be before \"to\"");
        }

        List<User> userList = new ArrayList<>();
        for (User user : users) {
            if (user.getBirthDate().isAfter(from) && user.getBirthDate().isBefore(to)) {
                userList.add(user);
            }
        }
        return userList;
    }

}
