package com.githubanalysis.controller;

import com.githubanalysis.annotation.LoggedInUser;
import com.githubanalysis.dtos.UserDto;
import com.githubanalysis.service.LoggedInUserService;
import com.githubanalysis.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoggedInUserController {

    private final LoggedInUserService loggedInUserService;

    @GetMapping("/user/me")
    public UserDto findMe(@LoggedInUser UserDetailsImpl userDetails) {

        return loggedInUserService.findByLoginId(userDetails.getLoginId());
    }
}
