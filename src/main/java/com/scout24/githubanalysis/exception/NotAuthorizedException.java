package com.scout24.githubanalysis.exception;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException() {
        super("Not authorized to access the external service, Please contact administrator");
    }
}
