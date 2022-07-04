package com.es.reportverse.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.TokenManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(path = "/api/senha/")

public class PasswordController {

    @Autowired
    AppUserService appUserService;

    @Autowired
    TokenManagerService tokenManagerService;

    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> triggerPasswordRecovery(@RequestBody String username, HttpServletRequest request) {
        return new ResponseEntity<>(tokenManagerService.simulateToken(username,request),HttpStatus.CREATED);
    }

}
