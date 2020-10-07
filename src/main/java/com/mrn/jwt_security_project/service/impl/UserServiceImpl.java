package com.mrn.jwt_security_project.service.impl;

import com.mrn.jwt_security_project.constants.UserServiceImplConstants;
import com.mrn.jwt_security_project.domain.User;
import com.mrn.jwt_security_project.domain.UserPrincipal;
import com.mrn.jwt_security_project.enumeration.Role;
import com.mrn.jwt_security_project.exception.domain.EmailExistsException;
import com.mrn.jwt_security_project.exception.domain.UsernameExistsException;
import com.mrn.jwt_security_project.repository.UserRepository;
import com.mrn.jwt_security_project.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            LOGGER.error("Username not found by username: " + username);
            throw new UsernameNotFoundException("Username not found by username: " + username);
        } else {
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user); // create a user principal
            LOGGER.info("Returning found user from by username: " + username);
            return userPrincipal;
        }
    }

    @Override
    public User register(String firstName, String lastName, String username, String email) throws EmailExistsException, UsernameExistsException {
        validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);

        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageURL());

        userRepository.save(user);
        LOGGER.info("New user password: " + password);

        return user;
    }

    private String getTemporaryProfileImageURL() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(UserServiceImplConstants.USER_IMAGE_PROFILE_TEMP).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10); // return a number in form of a string with the length of 10
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UsernameExistsException, EmailExistsException {

        User userByNewUsername = findByUserName(newUsername);
        User userByNewEmail = findByEmail(newEmail);

        if (StringUtils.isNotBlank(currentUsername)) {
            User currentUser = userRepository.findUserByUsername(currentUsername);
            if (currentUser == null) {
                throw new UsernameNotFoundException("No user found by username " + currentUsername);
            }

            if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId()))
                throw new UsernameExistsException(UserServiceImplConstants.USERNAME_ALREADY_EXISTS);

            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId()))
                throw new EmailExistsException(UserServiceImplConstants.EMAIL_ALREADY_EXISTS);

            return currentUser;
        } else {
            if (userByNewUsername != null)
                throw new UsernameExistsException(UserServiceImplConstants.USERNAME_ALREADY_EXISTS);

            if (userByNewEmail != null)
                throw new EmailExistsException(UserServiceImplConstants.EMAIL_ALREADY_EXISTS);
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
