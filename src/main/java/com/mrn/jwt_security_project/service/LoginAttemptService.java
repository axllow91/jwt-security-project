package com.mrn.jwt_security_project.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class LoginAttemptService {

    private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    private static final int ATTEMPT_INCREMENT = 1;

    private LoadingCache<String, Integer> loginAttemptCache;

    public LoginAttemptService() {
        // initialize the cache
        this.loginAttemptCache = CacheBuilder.newBuilder().expireAfterAccess(15, MINUTES).maximumSize(100)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    // remove user from the cache
    public void evictUserFromAttemptCache(String username) {
        loginAttemptCache.invalidate(username);
    }

    // attempts on user cached
    public void addUserToLoginAttemptCache(String username)  {
        int attempts = 0;
        try {
            attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(username);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        loginAttemptCache.put(username, attempts);
    }

    // check for the number of attempts
    // if user exceeded that maximum number
    public boolean hasExceededMaxAttempts(String username)  {
        try {
            return loginAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
