package com.project.springsecurity.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.springsecurity.entities.User;
import com.project.springsecurity.entities.dtos.ResponseDto;
import com.project.springsecurity.entities.dtos.UserFullDto;
import com.project.springsecurity.exceptions.UserAlreadyExistsException;
import com.project.springsecurity.exceptions.UserNotFoundOnDatabaseException;
import com.project.springsecurity.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createNewUser(@RequestBody UserFullDto newUser) {
        log.info("POST /user/create");

        if (newUser != null) {
            try {
                userService.save(new User(newUser));
                return new ResponseEntity<>(new ResponseDto("success"), HttpStatus.OK);
            } catch (UserAlreadyExistsException e) {
                log.warn("User already exists on database", e);
                return new ResponseEntity<>(new ResponseDto("alreadyExists"), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new ResponseDto("error"), HttpStatus.OK);
        }
    }

    @PostMapping("/setAdminUser")
    public ResponseEntity<User> setAdminUser(@RequestParam("id") Long id) {
        log.info("POST /user/setAdminUser");

        try {
            return new ResponseEntity<>(userService.addAdminRole(id), HttpStatus.OK);
        } catch (UserNotFoundOnDatabaseException e) {
            log.warn("User with id " + id + " not found on database.", e);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserFullDto>> getAllUsers() {
        log.info("GET /user/all");

        List<User> allUsers = userService.findAll();
        List<UserFullDto> allUsersDto = new ArrayList<>();

        for (User user : allUsers) {
            allUsersDto.add(new UserFullDto(user));
        }

        return new ResponseEntity<>(allUsersDto, HttpStatus.OK);
    }
}
