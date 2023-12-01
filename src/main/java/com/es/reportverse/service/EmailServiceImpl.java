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

    private static final String PASSWORD_RECOVERY_EMAIL_SENT = "Email foi enviado com sucesso";

    private static final String EXCLUDED_PUBLICATION_AUTHOR_NOTIFIED = "O autor da publicação foi notificado e a publicação foi excluída da plataforma.";

    private static final String AVAILABLE_PUBLICATION_AUTHOR_NOTIFIED = "O autor da publicação foi notificado e a publicação voltou ao ar na plataforma";

    private static final String PASSWORD_RECOVERY_DOMAIN = System.getenv("PASSWORD_RECOVERY_LINK");

    private final JavaMailSender emailSender;

    private final AppUserService appUserService;

    private void sendMail(String to, String subject, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setFrom(System.getenv("SPRING_MAIL_USER"));
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(
//                text
//        );
//        emailSender.send(message);
    }

    @Override
    public String sendPasswordRecoveryByEmail(String to, String recoveryLink) {
//        this.sendMail(
//                to,
//                "Link para recuperação de senha",
//                "Este é o link para recuperação de sua senha:  " + PASSWORD_RECOVERY_DOMAIN + recoveryLink
//        );

        return PASSWORD_RECOVERY_EMAIL_SENT;
    }

    @Override
    public void notifyAdminsReportedPublication(Publication publication) {
//        Collection<AppUser> admins = appUserService.findAllByUserRole(UserRole.ADMINISTRADOR);
//        for(AppUser admin : admins){
//            this.sendMail(
//                    admin.getUsername(),
//                    "Uma publicação foi denunciada por usuários como imprópria",
//                    "Caro " + admin.getName() + ", a publicação de identificação " + publication.getId()
//                            + " foi denunciada pelos usuários como imprópria. Favor realizar a verificação da mesma."
//            );
//        }
    }



    @Override
    public String notifyExcludedPublicationAuthor(Publication publication) {
//        String authorUsername = appUserService.getUser(publication.getAuthorId()).getUsername();
//
//        this.sendMail(
//                authorUsername,
//                "Uma publicação sua foi excluída do Reportverse",
//                "Recentemente, você criou uma publicação que havia sido reportada para administradores por usuários. " +
//                        "Após um período de análise, foi decidido que sua publicação não condiz com as diretrizes da plataforma, por isso foi excluída. " +
//                        "Solicitamos maior cuidado no conteúdo a ser postado em próximas publicações." +
//                        "\n\nConteúdo da publicação: \n" + publication.getDescription()
//        );

        return EXCLUDED_PUBLICATION_AUTHOR_NOTIFIED;
    }

    @Override
    public String notifyAvailablePublicationAuthor(Publication publication) {
//        String authorUsername = appUserService.getUser(publication.getAuthorId()).getUsername();
//
//        this.sendMail(
//            authorUsername,
//            "Uma publicação sua voltou ao ar no Reportverse",
//            "Recentemente, você criou uma publicação que havia sido reportada para administradores por usuários. " +
//                    "Após um período de análise, foi decidido que sua publicação pode continuar na plataforma. " +
//                    "\n\nConteúdo da publicação: \n" + publication.getDescription()
//        );

        return AVAILABLE_PUBLICATION_AUTHOR_NOTIFIED;
    }

    @Override
    public void notifyAuthorReportedPublication(Publication publication) {
//        String authorUsername = appUserService.getUser(publication.getAuthorId()).getUsername();
//
//        this.sendMail(
//                authorUsername,
//                "Uma publicação sua foi suspensa do Reportverse",
//                "Recentemente, você criou uma publicação que foi reportada por usuários como imprópria para " +
//                        "permanência no Reportverse. Agora, nossos usuários administradores irão avaliar a integridade do conteúdo." +
//                        " Solicitamos maior cuidado no conteúdo a ser postado em próximas publicações." +
//                        "\n\nConteúdo da publicação: \n" + publication.getDescription()
//        );
    }


}