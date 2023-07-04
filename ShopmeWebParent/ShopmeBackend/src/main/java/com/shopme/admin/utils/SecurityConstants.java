package com.shopme.admin.utils;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {
    public static final String LOGIN_URL = "/login";
    public static final String REGISTER = "/api/v1/account/new";
    public static final String CURRENT_USER = "current_user";
    public static final String REFRESH_TOKEN_URL = "/api/v1/account/refresh-token";
    public static final String MY_SECRET_KEY = "gdsfhkjldjsjflksjkf";
    public static final int TIME_EXPIRED_ACCESS_TOKEN = 5;
    public static final int TIME_EXPIRED_REFRESH_TOKEN = 24;
    @Value("${my_config.domain}")
    public static final String MY_DOMAIN = "";

}
