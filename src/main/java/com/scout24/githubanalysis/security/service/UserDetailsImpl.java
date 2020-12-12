package com.scout24.githubanalysis.security.service;

import com.scout24.githubanalysis.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

@Data
@NoArgsConstructor
public class UserDetailsImpl implements OAuth2User, UserDetails {
    private Long id;
    private String loginId;
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    private UserDetailsImpl(Long id, String loginId, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.loginId = loginId;
        this.authorities = authorities;
    }

    public static UserDetailsImpl get(final User user) {
        List<GrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority("USER_ROLE"));
        return new UserDetailsImpl(user.getId(), user.getLoginId(), authorities);
    }

    public static UserDetailsImpl get(User user, Map<String, Object> attributes) {
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.get(user);
        userDetailsImpl.setAttributes(attributes);
        return userDetailsImpl;
    }

    @Override
    public String getPassword() {
        // Password not required
        return "";
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}
