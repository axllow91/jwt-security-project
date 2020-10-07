package com.mrn.jwt_security_project.controller;

import com.mrn.jwt_security_project.exception.ExceptionHandling;
import com.mrn.jwt_security_project.exception.domain.EmailNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/", "/user"})
public class UserController extends ExceptionHandling {

    @GetMapping("/home")
    public String showUser() throws EmailNotFoundException {
        //return "application works";
        throw new EmailNotFoundException("This email address is already taken");
    }

}
