package com.es.reportverse.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@AllArgsConstructor
public class TokenManagerServiceImpl implements TokenManagerService {

    AppUserService appUserService;

    public AppUser decodeAppUserToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String userToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(userToken);
                String username = decodedJWT.getSubject();
                return appUserService.getUser(username);

            } catch (Exception e) {
                throw new ApiRequestException(e.getMessage());
            }
        } else {
            throw new ApiRequestException("AppUser token is missing");
        }
    }

}
