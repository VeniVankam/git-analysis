package com.githubanalysis.security;

import java.util.Map;

public abstract class AuthorizationProviderUserInfo {
    Map<String, Object> attributes;

    AuthorizationProviderUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getLoginId();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public abstract String getLocation();
}
