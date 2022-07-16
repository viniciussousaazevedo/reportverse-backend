package com.es.reportverse.service;

import com.es.reportverse.DTO.UserRegistrationDTO;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface AppUserService extends UserDetailsService {

    AppUser registerUser(UserRegistrationDTO userRegistrationDTO);

    void saveUser(AppUser appUser);

    AppUser getUser(String username);

    AppUser getUserByRecoveryPasswordToken(String recoveryPasswordToken);

    void checkPasswordConfirmation(String password, String passwordConfirmation);

    Collection<AppUser> findAllAdmins();

    Collection<AppUser> findAllByUserRole(UserRole userRole);

    Publication updatePublicationsToCheck(Collection<AppUser> admins, Publication publication);

}
