package com.es.reportverse.security.config;


public class TokenConstants {

    public static final String[] NO_TOKEN_ENDPOINTS = {
            "/api/login",
            "/api/usuario/cadastro",
            "/api/usuario/token/refresh",
            "/api/senha/esqueci-senha",
            "/api/senha/trocar-senha",
    };

    public static final String BEARER = "Bearer ";

    public static String SECRET_WORD_FOR_TOKEN_GENERATION = System.getenv("SECRET_WORD_FOR_TOKEN_GENERATION");

    public static final int MINUTES_FOR_TOKEN_EXPIRATION = 20000;

    public static final String PERMIT_ALL_AFTER = "/**";

    public static final String[] SWAGGER_ENDPOINTS = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/webjars/**"
    };

}
