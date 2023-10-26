package com.es.reportverse.controller;

import com.es.reportverse.DTO.PasswordRecoveryDTO;
import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.EmailService;
import com.es.reportverse.service.PasswordService;
import com.es.reportverse.service.TokenManagerService;
import com.es.reportverse.utils.CustomLogger;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/senha/")
@AllArgsConstructor
public class PasswordController {

    private final String CLASS = "PasswordController:";

    CustomLogger logger;

    private static final String SUCCESSFULLY_CHANGED_PASSWORD = "Senha alterada com sucesso.";

    AppUserService appUserService;

    TokenManagerService tokenManagerService;

    EmailService emailService;

    PasswordService passwordService;

    private String setContext(String method) {
        return CLASS + ":" + method;
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> triggerPasswordRecovery(@RequestBody String username) {
        logger.setContext(this.setContext("triggerPasswordRecovery"));
        logger.logMethodStart(username);

        String userEndpoint = passwordService.getPasswordRecoveryLink(username);

        String message = emailService.sendPasswordRecoveryByEmail(username,userEndpoint);

        logger.logMethodEnd(message);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PostMapping("/trocar-senha/{userToken}")
    public ResponseEntity<?> setNewPassword(@RequestBody PasswordRecoveryDTO passwordRecoveryDTO, @PathVariable("userToken") String token){
        logger.setContext(this.setContext("setNewPassword"));
        logger.logMethodStart(token);
        passwordService.setNewPassword(passwordRecoveryDTO, token);

        logger.logMethodEnd(SUCCESSFULLY_CHANGED_PASSWORD);
        return new ResponseEntity<>(SUCCESSFULLY_CHANGED_PASSWORD, HttpStatus.OK);
    }


}
