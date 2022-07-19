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

    final String REPORTED_PUBLICATION_AUTHOR_NOTIFIED = "O autor da publicação foi notificado e a publicação foi excluída da plataforma.";
    
    private JavaMailSender emailSender;

    private AppUserService appUserService;

    private void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(System.getenv("MAIL_USER"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(
                text
        );
        emailSender.send(message);
    }

    @Override
    public String sendPasswordRecoveryByEmail(String to, String recoveryLink) {
        this.sendMail(
                to,
                "Link para recuperação de senha",
                "Este é o link para recuperação de sua senha:  " + recoveryLink
        );

        return PASSWORD_RECOVERY_EMAIL_SENT;
    }

    @Override
    public void notifyAdminsReportedPublication(Publication publication) {
        Collection<AppUser> admins = appUserService.findAllByUserRole(UserRole.ADMINISTRADOR);
        for(AppUser admin : admins){
            this.sendMail(
                    admin.getUsername(),
                    "Uma publicação foi denunciada por usuários como imprópria",
                    "Caro " + admin.getName() + ", a publicação de identificação " + publication.getId()
                            + " foi denunciada pelos usuários como imprópria. Favor realizar a verificação da mesma."
            );
        }
    }

    @Override
    public String notifyReportedPublicationAuthor(String authorUsername, Publication publication) {

        this.sendMail(
            authorUsername,
                "Uma publicação sua foi excluída do Reportverse",
                "Você criou uma publicação que foi recentemente excluída do Reportverse " +
                        "por não estar de acordo com a proposta da plataforma. " +
                        "Solicitamos que tome mais cuidado com próximas publicações\n\n" +
                        "Conteúdo da pubilcação excluída:\n" + publication.getDescription()
        );

        return REPORTED_PUBLICATION_AUTHOR_NOTIFIED;
    }


}