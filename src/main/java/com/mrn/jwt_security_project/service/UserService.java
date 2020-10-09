package com.mrn.jwt_security_project.service;


import com.mrn.jwt_security_project.domain.User;
import com.mrn.jwt_security_project.exception.domain.EmailExistException;
import com.mrn.jwt_security_project.exception.domain.EmailNotFoundException;
import com.mrn.jwt_security_project.exception.domain.UserNotFoundException;
import com.mrn.jwt_security_project.exception.domain.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User addNewUser(String firstName, String lastName, String username,
                    String email, String role, boolean isNonLocked,
                    boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

    User updateUser(String currentUsername,String newFirstName, String newLastName, String newUsername,
                    String newEmail, String role, boolean isNonLocked,
                    boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

    void deleteUser(long id);

    void resetPassword(String email) throws EmailNotFoundException;

    User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
}
