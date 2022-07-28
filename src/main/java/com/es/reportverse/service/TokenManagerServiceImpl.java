package com.es.reportverse.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Getter
public class TokenManagerServiceImpl implements TokenManagerService {

    private final String MISSING_TOKEN = "the token for this request is missing or it is incomplete";
    private final String BEARER = "Bearer ";
    private final String SECRET_WORD_FOR_TOKEN_GENERATION;
    public final int MINUTES_FOR_TOKEN_EXPIRATION = 20000;

    AppUserService appUserService;

    public TokenManagerServiceImpl(AppUserService appUserService) {
        this.appUserService = appUserService;

        Dotenv dotenv = Dotenv.configure().load();
        this.SECRET_WORD_FOR_TOKEN_GENERATION = dotenv.get("SECRET_WORD_FOR_TOKEN_GENERATION");
    }


    @Override
    public AppUser decodeToken(String token, Algorithm algorithm) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();

        return appUserService.getUser(username);
    }

    @Override
    public void tokenDecodeError(Exception e, HttpServletResponse response) throws IOException {
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    @Override
    public String createAppUserToken(HttpServletRequest request, Authentication authentication) {
        AppUser appUser = (AppUser) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        return JWT.create()
                .withSubject(appUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + MINUTES_FOR_TOKEN_EXPIRATION * 60 * 1000))
                .withIssuer(request.getRequestURI())
                .withClaim("userRole", appUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public AppUser decodeAppUserToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(SECRET_WORD_FOR_TOKEN_GENERATION.getBytes());
                String token = authorizationHeader.substring(BEARER.length());
                return decodeToken(token, algorithm);

            } catch (Exception e) {
                throw new ApiRequestException(e.getMessage());
            }
        } else {
            throw new ApiRequestException(MISSING_TOKEN);
        }
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO ajeitar tempo de expiração de código para 30 minutos!
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            try {
                String refresh_token = authorizationHeader.substring(BEARER.length());
                Algorithm algorithm = Algorithm.HMAC256(SECRET_WORD_FOR_TOKEN_GENERATION.getBytes());
                AppUser user = decodeToken(refresh_token, algorithm);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + MINUTES_FOR_TOKEN_EXPIRATION * 60 * 1000))
                        .withIssuer(request.getRequestURI())
                        .withClaim("userRole", user.getUserRole().toString())
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                tokenDecodeError(e, response);
            }
        } else {
            throw new ApiRequestException(MISSING_TOKEN);
        }
    }

}
