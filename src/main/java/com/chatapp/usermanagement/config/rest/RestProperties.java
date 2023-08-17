package com.chatapp.usermanagement.config.rest;

public final class RestProperties {

    public static final String ROOT = "/api";

    public static final  String TOKEN_PREFIX = "Bearer ";


    public static final class AUTH {

        public static final String ROOT = "/auth";

        public static final String CHECK_TOKEN = "/check-token";
    }


    public static final class USER {

        public static final String ROOT = "/users";

        public static final String REGISTER = "/register";

        public static final String LOGIN = "/login";

        public static final String DETAILS = "/details";
    }

    public static final class MEDIA {
        public static final String ROOT = "/media";


    }
}
