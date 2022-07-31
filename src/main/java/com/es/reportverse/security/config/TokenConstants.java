package com.es.reportverse.security.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TokenConstants {

    public static final List<String> NO_TOKEN_ENDPOINTS = Collections.unmodifiableList(new ArrayList<>(Arrays.asList("/api/login",
            "/api/usuario/cadastro",
            "/api/usuario/token/refresh",
            "/api/senha/esqueci-senha",
            "/api/senha/trocar-senha")));

    public static final String BEARER = "Bearer ";

    public static final String SECRET_WORD_FOR_TOKEN_GENERATION = System.getenv("SECRET_WORD_FOR_TOKEN_GENERATION");

    public static final int MINUTES_FOR_TOKEN_EXPIRATION = 20000;

    public static final String PERMIT_ALL_AFTER = "/**";

    public static final List<String> SWAGGER_ENDPOINTS =Collections.unmodifiableList(new ArrayList<>(Arrays.asList("/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/webjars/**")));
}
