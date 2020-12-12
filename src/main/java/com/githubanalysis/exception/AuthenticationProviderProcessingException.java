package com.githubanalysis.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationProviderProcessingException extends AuthenticationException {
    public AuthenticationProviderProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthenticationProviderProcessingException(String msg) {
        super(msg);
    }
}
