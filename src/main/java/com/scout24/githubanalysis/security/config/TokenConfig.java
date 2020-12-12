package com.scout24.githubanalysis.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
@Getter
@Setter
public class TokenConfig {
    private String tokenSecret;
    private long tokenExpiresInMilliSecs;
}
