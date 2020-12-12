package com.scout24.githubanalysis.security.service;

import com.scout24.githubanalysis.exception.AuthenticationProviderProcessingException;
import com.scout24.githubanalysis.dtos.AuthenticationProvider;
import com.scout24.githubanalysis.domain.User;
import com.scout24.githubanalysis.security.AuthorizationProviderUserInfo;
import com.scout24.githubanalysis.security.AuthorizationProviderFactory;
import com.scout24.githubanalysis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomAuthorizationUserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new RuntimeException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        AuthorizationProviderUserInfo authorizationProviderUserInfo = AuthorizationProviderFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(authorizationProviderUserInfo.getLoginId())) {
            throw new AuthenticationProviderProcessingException("Login information not found from the provider");
        }
        Optional<User> userOptional = userRepository.findByLoginId(authorizationProviderUserInfo.getLoginId());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            user = updateExistingUser(user, authorizationProviderUserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, authorizationProviderUserInfo);
        }

        return UserDetailsImpl.get(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, AuthorizationProviderUserInfo authorizationProviderUserInfo) {
        User user = new User();
        user.setName(authorizationProviderUserInfo.getName());
        user.setLoginId(authorizationProviderUserInfo.getLoginId());
        user.setEmail(authorizationProviderUserInfo.getEmail());
        user.setImage(authorizationProviderUserInfo.getImageUrl());
        user.setProvider(AuthenticationProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(authorizationProviderUserInfo.getId());
        user.setLocation(authorizationProviderUserInfo.getLocation());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, AuthorizationProviderUserInfo authorizationProviderUserInfo) {
        existingUser.setName(authorizationProviderUserInfo.getName());
        existingUser.setImage(authorizationProviderUserInfo.getImageUrl());
        existingUser.setLoginId(authorizationProviderUserInfo.getLoginId());
        existingUser.setLocation(authorizationProviderUserInfo.getLocation());
        existingUser.setEmail(authorizationProviderUserInfo.getEmail());
        return userRepository.save(existingUser);
    }

}
