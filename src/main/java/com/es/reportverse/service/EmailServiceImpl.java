package com.es.reportverse.service;

import com.es.reportverse.enums.UserRole;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    final String PASSWORD_RECOVERY_EMAIL_SENT = "Email foi enviado com sucesso";

    final String NOTIFY_ADMINS_EMAILS_SENDED = "Administradores foram notificados a respeito da publicação."
    
    private JavaMailSender emailSender;

    private AppUserService appUserService;

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

    @Override
    public String notifyAdminsReportedPublication(Publication publication) {
        Collection<AppUser> admins = appUserService.findAllByUserRole(UserRole.ADMINISTRADOR);
        for(AppUser admin : admins){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("reportverse1@gmail.com");
            message.setTo(admin.getUsername());
            message.setSubject("Uma publicação foi denunciada por usuários como imprópria" );
            message.setText(
                    "Caro " + admin.getName() + " , a publicação de identificação " + publication.getId()
                    + "foi denunciada pelos usuários como imprópria. Favor realizar a verificação da mesma."
            );
            emailSender.send(message);
        }
        return NOTIFY_ADMINS_EMAILS_SENDED;
    }

}