package com.githubanalysis.security;


import com.githubanalysis.exception.AuthenticationProviderProcessingException;
import com.githubanalysis.dtos.AuthenticationProvider;

import java.util.Map;

public class AuthorizationProviderFactory {

    public static AuthorizationProviderUserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) {
        if (provider.equalsIgnoreCase(AuthenticationProvider.github.toString())) {
            return new GithubUserInfo(attributes);
        } else {
            throw new AuthenticationProviderProcessingException("Sorry! Login with " + provider + " is not supported yet.");
        }
    }
}
