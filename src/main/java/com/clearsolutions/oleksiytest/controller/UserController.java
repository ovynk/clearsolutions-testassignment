package com.clearsolutions.oleksiytest.controller;

import com.clearsolutions.oleksiytest.model.User;
import com.clearsolutions.oleksiytest.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid User user) {
        user = userService.create(user);

        return ResponseEntity
                .created(linkTo(UserController.class).slash(user.getEmail()).toUri())
                .build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<RepresentationModel<?>> readUser(@PathVariable("email") String email) {
        User user = userService.findByEmail(email);
        Link link = linkTo(UserController.class).slash(user.getEmail()).withSelfRel();

        return new ResponseEntity<>(RepresentationModel.of(user).add(link), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{email}")
    public ResponseEntity<RepresentationModel<?>> patchUser(@PathVariable String email, @RequestBody User user) {
        user = userService.patch(email, user);
        Link link = linkTo(UserController.class).slash(user.getEmail()).withSelfRel();

        return new ResponseEntity<>(RepresentationModel.of(user).add(link), HttpStatus.OK);
    }

    @DeleteMapping( "/{email}")
    public ResponseEntity<User> deleteUser(@PathVariable("email") String email) {
        userService.delete(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<RepresentationModel<?>>> getUsersByBirthRange(
            @RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate from,
            @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate to) {
        List<User> users = userService.getUsersByBirthRange(from, to);
        List<RepresentationModel<?>> representationModelList = new ArrayList<>();

        for (User user : users) {
            Link link = linkTo(UserController.class).slash(user.getEmail()).withSelfRel();
            representationModelList.add(RepresentationModel.of(user).add(link));
        }

        Link link = linkTo(UserController.class).withSelfRel();
        return new ResponseEntity<>(CollectionModel.of(representationModelList, link), HttpStatus.OK);
    }

}