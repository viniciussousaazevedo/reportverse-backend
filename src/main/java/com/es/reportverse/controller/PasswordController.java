package com.es.reportverse.controller;

import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.EmailService;
import com.es.reportverse.service.PasswordService;
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

    @Autowired
    EmailService emailService;

    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> triggerPasswordRecovery(@RequestBody String username, HttpServletRequest request) {
        // enviar email com a rota :
        // localhost:8080/api/senha/trocar-senha/{token}
        String token = tokenManagerService.simulateToken(username,request);
        String recoveryLink = "localhost:8080/api/senha/trocar-senha/" + token;
        String message = emailService.sendPasswordRecoveryEmail(username,recoveryLink);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

}
