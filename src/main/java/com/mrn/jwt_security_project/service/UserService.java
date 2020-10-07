package com.mrn.jwt_security_project.service;



import com.mrn.jwt_security_project.domain.User;
import com.mrn.jwt_security_project.exception.domain.EmailExistsException;
import com.mrn.jwt_security_project.exception.domain.UsernameExistsException;

import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String username, String email) throws EmailExistsException, UsernameExistsException;

    List<User> getUsers();

    User findByUserName(String username);

    User findByEmail(String email);

}
