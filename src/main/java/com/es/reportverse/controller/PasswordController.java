package com.es.reportverse.controller;

import com.es.reportverse.model.AppUser;
import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.EmailService;
import com.es.reportverse.service.PasswordService;
import com.es.reportverse.service.TokenManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/trocar-senha/{userToken}")
    public ResponseEntity<?> setNewPassword(@RequestBody String newPassword, @PathVariable("userToken") String token, HttpServletRequest request){
        AppUser user = tokenManagerService.decodeAppUserToken(request);
        user.setPassword(newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
