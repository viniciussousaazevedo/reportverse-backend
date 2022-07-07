package com.es.reportverse.service;

import com.es.reportverse.DTO.UserRegistrationDTO;
import com.es.reportverse.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    AppUser registerUser(UserRegistrationDTO userRegistrationDTO);

    void saveUser(AppUser appUser);

    AppUser getUser(String username);

    AppUser getUserByRecoveryPasswordToken(String recoveryPasswordToken);

    void checkPasswordConfirmation(String password, String passwordConfirmation);

}
