package com.clearsolutions.oleksiytest.controller;

import com.clearsolutions.oleksiytest.model.User;
import com.clearsolutions.oleksiytest.service.UserService;
import com.clearsolutions.oleksiytest.utils.NullAwareBeanUtilsBean;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> readUser(@PathVariable("email") String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user) {
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @PatchMapping("/{email}")
    public ResponseEntity<User> patchUser(@PathVariable String email, @RequestBody User user)
            throws InvocationTargetException, IllegalAccessException {
        User userToPatch = userService.findByEmail(email);

        new NullAwareBeanUtilsBean().copyProperties(userToPatch, user);

        return new ResponseEntity<>(userToPatch, HttpStatus.OK);
    }

    @DeleteMapping( "/{email}")
    public ResponseEntity<User> deleteUser(@PathVariable("email") String email) {
        userService.delete(userService.findByEmail(email));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsersByBirthRange(
            @RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate from,
            @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate to) {
        return new ResponseEntity<>(userService.getUsersByBirthRange(from, to), HttpStatus.OK);
    }

}