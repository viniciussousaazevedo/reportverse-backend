package com.es.reportverse.service;

import com.es.reportverse.DTO.PasswordRecoveryDTO;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService{

    private static final int MINUTES_FOR_PASS_REC_TOKEN_EXPIRATION = 15;
    private static final String RECOVERY_TOKEN_EXPIRED = "Este link já expirou. Solicite uma nova recuperação de senha";
    private static final String PASSWORD_RECOVERY_PATH = System.getenv("PASSWORD_RECOVERY_PATH") + "%s";

    private AppUserService appUserService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String getPasswordRecoveryLink(String username) {
        AppUser user = appUserService.getUser(username);

        user.setRecoveryPasswordToken(UUID.randomUUID().toString());
        user.setRecoveryPasswordTokenExpiration(new Date(System.currentTimeMillis() + MINUTES_FOR_PASS_REC_TOKEN_EXPIRATION * 60 * 1000));
        appUserService.saveUser(user);

        return String.format(PASSWORD_RECOVERY_PATH, user.getRecoveryPasswordToken());
    }

    @Override
    public void setNewPassword(PasswordRecoveryDTO passwordRecoveryDTO, String token) {
        AppUser user = appUserService.getUserByRecoveryPasswordToken(token);

        if (new Date(System.currentTimeMillis()).after(user.getRecoveryPasswordTokenExpiration())) {
            throw new ApiRequestException(RECOVERY_TOKEN_EXPIRED);
        }

        this.appUserService.checkPasswordConfirmation(
                passwordRecoveryDTO.getPassword(),
                passwordRecoveryDTO.getPasswordConfirmation()
        );

        user.setPassword(bCryptPasswordEncoder.encode(passwordRecoveryDTO.getPassword()));
        appUserService.saveUser(user);
    }


}
