package com.mrn.jwt_security_project.listener;

import com.mrn.jwt_security_project.domain.User;
import com.mrn.jwt_security_project.domain.UserPrincipal;
import com.mrn.jwt_security_project.service.LoginAttemptService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener {
    private final LoginAttemptService loginAttemptService;

    public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) event.getAuthentication().getPrincipal();
            loginAttemptService.evictUserFromAttemptCache(userPrincipal.getUsername());
        }
    }

}
