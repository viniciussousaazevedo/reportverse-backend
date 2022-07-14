package com.es.reportverse.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    final String PASSWORD_RECOVERY_EMAIL_SENT = "Email foi enviado com sucesso";
    
    private JavaMailSender emailSender;

    @Override
    public String sendPasswordRecoveryByEmail(String to, String recoveryLink) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("reportverse1@gmail.com");
        message.setTo(to);
        message.setSubject("Link para recuperação de senha");
        message.setText(
                "Este é o link para recuperação de sua senha:  " + recoveryLink
                       );
        emailSender.send(message);
        return PASSWORD_RECOVERY_EMAIL_SENT;
    }
}