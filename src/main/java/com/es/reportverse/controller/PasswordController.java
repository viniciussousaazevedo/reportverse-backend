package com.es.reportverse.controller;

import com.es.reportverse.DTO.PasswordRecoveryDTO;
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

@RestController
@RequestMapping(path = "/api/senha/")

public class PasswordController {

    private final String SUCCESSFULLY_CHANGED_PASSWORD = "Senha alterada com sucesso.";

    @Autowired
    AppUserService appUserService;

    @Autowired
    TokenManagerService tokenManagerService;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordService passwordService;

    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> triggerPasswordRecovery(@RequestBody String username) {

        String link = passwordService.getPasswordRecoveryLink(username);

        String message = emailService.sendPasswordRecoveryByEmail(username,link);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PostMapping("/trocar-senha/{userToken}")
    public ResponseEntity<?> setNewPassword(@RequestBody PasswordRecoveryDTO passwordRecoveryDTO, @PathVariable("userToken") String token){
        passwordService.setNewPassword(passwordRecoveryDTO, token);

        return new ResponseEntity<>(SUCCESSFULLY_CHANGED_PASSWORD, HttpStatus.OK);
    }


}
