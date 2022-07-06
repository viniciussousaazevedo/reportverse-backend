package com.es.reportverse.service;

import com.es.reportverse.DTO.PasswordRecoveryDTO;

public interface PasswordService {

    String getPasswordRecoveryLink(String username);

    void setNewPassword(PasswordRecoveryDTO passwordRecoveryDTO, String token);

    void checkPasswordConfirmation(String password, String passwordConfirmation);
}
