package com.scout24.githubanalysis.service;

import com.scout24.githubanalysis.domain.User;
import com.scout24.githubanalysis.dtos.UserDto;
import com.scout24.githubanalysis.exception.NotFoundException;
import com.scout24.githubanalysis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoggedInUserService {

    private final UserRepository userRepository;

    public UserDto findByLoginId(final String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NotFoundException(String.format("login id %s not found", loginId)));
        return toUserDTO(user);
    }

    private static UserDto toUserDTO(final User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
