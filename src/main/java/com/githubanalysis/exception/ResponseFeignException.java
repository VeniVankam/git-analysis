package com.githubanalysis.exception;

import feign.Response;

public class ResponseFeignException extends RuntimeException {
    private final Response response;

    public ResponseFeignException(String methodKey, Response response) {
        super(String.format("Java method that invoked the request: %s.\nResponse from the failed request: %s", methodKey, response));
        this.response = response;
    }

    public Response getResponse() {
        return this.response;
    }
}
