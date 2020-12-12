package com.scout24.githubanalysis.controller;

import com.scout24.githubanalysis.repository.UserRepository;
import com.scout24.githubanalysis.security.service.TokenService;
import com.scout24.githubanalysis.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;

class AbstractIntegrationTest {

    protected static final String TEST_LOGIN_ID = "testIntegration";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    protected String getToken() {
        UserDetailsImpl userDetails = UserDetailsImpl.get(userRepository.findByLoginId(TEST_LOGIN_ID).get());
        return tokenService.generateToken(userDetails);
    }
}
