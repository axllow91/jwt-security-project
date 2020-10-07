package com.mrn.jwt_security_project.service;


import com.mrn.jwt_security_project.domain.User;
import com.mrn.jwt_security_project.exception.domain.EmailExistException;
import com.mrn.jwt_security_project.exception.domain.UserNotFoundException;
import com.mrn.jwt_security_project.exception.domain.UsernameExistException;

import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
