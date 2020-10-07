package com.mrn.jwt_security_project.controller;

import com.mrn.jwt_security_project.domain.User;
import com.mrn.jwt_security_project.exception.ExceptionHandling;
import com.mrn.jwt_security_project.exception.domain.EmailExistsException;
import com.mrn.jwt_security_project.exception.domain.EmailNotFoundException;
import com.mrn.jwt_security_project.exception.domain.UsernameExistsException;
import com.mrn.jwt_security_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"/", "/user"})
public class UserController extends ExceptionHandling {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws EmailNotFoundException, EmailExistsException, UsernameExistsException {
        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

}
