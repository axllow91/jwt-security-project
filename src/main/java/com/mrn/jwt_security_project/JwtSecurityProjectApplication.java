package com.mrn.jwt_security_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;

import static com.mrn.jwt_security_project.constant.FileConstant.USER_FOLDER;

@SpringBootApplication
public class JwtSecurityProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityProjectApplication.class, args);
        new File(USER_FOLDER).mkdirs();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
