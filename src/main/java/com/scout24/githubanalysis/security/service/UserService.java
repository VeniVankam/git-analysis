package com.scout24.githubanalysis.security.service;


import com.scout24.githubanalysis.exception.NotFoundException;
import com.scout24.githubanalysis.domain.User;
import com.scout24.githubanalysis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with loginId : " + loginId)
        );

        return UserDetailsImpl.get(user);
    }

    UserDetails loadUserById(Long id) {
            User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", id)));

        return UserDetailsImpl.get(user);
    }
}