package com.scout24.githubanalysis.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Slf4j
@Primary
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return getCustomResponseCodeException(methodKey, response);
    }

    private Exception getCustomResponseCodeException(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                log.error("Received 404 while calling external service for methodKey={}, response={}", methodKey, response);
                return new NotFoundException();
            case 403:
                log.error("Received 403 while calling external service for methodKey={}, response={}", methodKey, response);
                return new NotAuthorizedException();
            case 401:
                log.error("Received 401 while calling external service for methodKey={}, response={}", methodKey, response);
                return new NotFoundException();
            default:
                log.info("Exception while calling underlying service for methodKey={}, response={}", methodKey, response);
                return new ResponseFeignException(methodKey, response);
        }
    }

}
