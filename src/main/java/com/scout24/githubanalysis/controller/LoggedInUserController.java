package com.scout24.githubanalysis.controller;

import com.scout24.githubanalysis.dtos.UserDto;
import com.scout24.githubanalysis.security.service.UserDetailsImpl;
import com.scout24.githubanalysis.service.LoggedInUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scout24.githubanalysis.annotation.LoggedInUser;

@RestController
@RequiredArgsConstructor
public class LoggedInUserController {

    private final LoggedInUserService loggedInUserService;

    @GetMapping("/user/me")
    public UserDto findMe(@LoggedInUser UserDetailsImpl userDetails) {

        return loggedInUserService.findByLoginId(userDetails.getLoginId());
    }
}
